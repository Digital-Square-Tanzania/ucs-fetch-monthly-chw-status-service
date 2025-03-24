package tz.go.moh.domain;

import java.util.List;

/**
 * Represents a request to check the eligibility of Community Health Workers (CHWs) for a specific period.
 * This class encapsulates the period and a list of CHW details for which eligibility needs to be checked.
 */
public class EligibleChwCheckRequest {

    private Period period;
    private List<ChwDetails> chwEligibilities;

    /**
     * Default constructor for EligibleChwCheckRequest.
     */
    public EligibleChwCheckRequest() {}

    /**
     * Constructs an EligibleChwCheckRequest with the specified period and CHW details.
     *
     * @param period           The period for which to check CHW eligibility.
     * @param chwEligibilities The list of CHW details to check.
     */
    public EligibleChwCheckRequest(Period period, List<ChwDetails> chwEligibilities) {
        this.period = period;
        this.chwEligibilities = chwEligibilities;
    }

    /**
     * Gets the period for which CHW eligibility is being checked.
     *
     * @return The period.
     */
    public Period getPeriod() {
        return period;
    }

    /**
     * Sets the period for which CHW eligibility is being checked.
     *
     * @param period The period to set.
     */
    public void setPeriod(Period period) {
        this.period = period;
    }

    /**
     * Gets the list of CHW details for which eligibility is being checked.
     *
     * @return The list of CHW details.
     */
    public List<ChwDetails> getChws() {
        return chwEligibilities;
    }

    /**
     * Sets the list of CHW details for which eligibility is being checked.
     *
     * @param chwEligibilities The list of CHW details to set.
     */
    public void setChws(List<ChwDetails> chwEligibilities) {
        this.chwEligibilities = chwEligibilities;
    }

    /**
     * Returns a string representation of the EligibleChwCheckRequest object.
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "DataContainer{" +
                "period=" + period +
                ", chws=" + chwEligibilities +
                '}';
    }
}
