package tz.go.moh.actors;

import tz.go.moh.domain.ChwDetails;
import tz.go.moh.domain.ChwEligibilityResults;
import tz.go.moh.domain.EligibleChwCheckRequest;
import tz.go.moh.util.DatabaseConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@code CheckChwEligibilityStatusProcessor} class is responsible for determining the eligibility status of
 * Community Health Workers (CHWs) based on their service activity within a specified period.
 * <p>
 * It interacts with a database to ascertain whether a CHW has performed any services during a given month.
 * The eligibility check involves querying multiple tables related to CHW services, including:
 * <ul>
 *     <li>{@code hps_client_services}</li>
 *     <li>{@code hps_household_services}</li>
 *     <li>{@code hps_death_registrations}</li>
 *     <li>{@code hps_mobilization_services}</li>
 * </ul>
 * <p>
 * The class provides a method {@link #checkEligibility(EligibleChwCheckRequest, String, int, String, String, String)}
 * to perform the eligibility check.
 */
public class CheckChwEligibilityStatusProcessor {
    /**
     * Checks the eligibility of a list of CHWs based on their service activity within a specified period.
     *
     * @param eligibleChwCheckRequest The request object containing the period and the list of CHWs to check.
     * @param serverName              The name of the database server.
     * @param port                    The port number of the database server.
     * @param databaseName            The name of the database.
     * @param username                The username for database authentication.
     * @param password                The password for database authentication.
     * @return A list of {@link ChwEligibilityResults} indicating the eligibility status of each CHW.
     */
    public List<ChwEligibilityResults> checkEligibility(EligibleChwCheckRequest eligibleChwCheckRequest, String serverName, int port, String databaseName, String username, String password) {
        // 1) Extract period and CHWs list from the request
        String month = eligibleChwCheckRequest.getPeriod().getMonth();
        String year = eligibleChwCheckRequest.getPeriod().getYear();
        List<ChwDetails> chws = eligibleChwCheckRequest.getChws();

        // 2) Build the list of queryIds from the CHWs (using OpenmrsProviderId)
        List<String> queryIds = new ArrayList<>();
        for (ChwDetails chw : chws) {
            queryIds.add(chw.getOpenmrsProviderId());
        }

        // 3) Compute date boundaries:
        //    startDate: first day of the given month,
        //    endDate: first day of the next month.
        String startDateStr = String.format("%s-%02d-01", year, Integer.parseInt(month));
        int monthInt = Integer.parseInt(month);
        int yearInt = Integer.parseInt(year);
        monthInt++; // move to next month
        if (monthInt > 12) {
            monthInt = 1;
            yearInt++;
        }
        String endDateStr = String.format("%d-%02d-01", yearInt, monthInt);

        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);

        // 4) Build the SQL query string.
        //    Create the IN clause dynamically from the queryIds list.
        String inClause = queryIds.stream()
                .map(id -> "'" + id + "'")
                .collect(Collectors.joining(","));

        String sql = "SELECT provider_id FROM (" +
                "   SELECT provider_id, event_date FROM hps_client_services " +
                "   UNION ALL " +
                "   SELECT provider_id, event_date FROM hps_household_services " +
                "   UNION ALL " +
                "   SELECT provider_id, event_date FROM hps_death_registrations " +
                "   UNION ALL " +
                "   SELECT provider_id, event_date FROM hps_mobilization_services " +
                ") t " +
                "WHERE provider_id IN (" + inClause + ") " +
                "AND event_date >= ? " +
                "AND event_date < ?;";

        // 5) Query the database to collect eligible provider IDs.
        Set<String> eligibleProviders = new HashSet<>();
        try (Connection conn = DatabaseConnectionFactory.getConnection(serverName, port, databaseName, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set parameters and execute your query
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            try (ResultSet rs = stmt.executeQuery()) {
                // Process the results
                while (rs.next()) {
                    eligibleProviders.add(rs.getString("provider_id"));
                }
            }
        } catch (SQLException e) {
            // Handle exceptions appropriately
            throw new RuntimeException("Database error during eligibility check", e);
        }

        // 6) Build the output JSON array.
        //    Each element uses the NationalIdentificationNumber from the request and indicates eligibility.
        List<ChwEligibilityResults> chwEligibilityResults = new ArrayList<>();
        for (ChwDetails chw : chws) {
            chwEligibilityResults.add(new ChwEligibilityResults(chw.getNationalIdentificationNumber(), eligibleProviders.contains(chw.getOpenmrsProviderId())));
            ;
        }

        return chwEligibilityResults;
    }
}
