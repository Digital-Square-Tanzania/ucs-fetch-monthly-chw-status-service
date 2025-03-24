package tz.go.moh.util;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionFactory {
    /**
     * Establishes a database connection using the provided credentials.
     *
     * @param serverName   The name of the database server.
     * @param port         The port number the database server is listening on.
     * @param databaseName The name of the database to connect to.
     * @param username     The username for database authentication.
     * @param password     The password for database authentication.
     * @return A Connection object representing the database connection.
     * @throws SQLException If a database access error occurs or the url is null.
     * @see java.sql.DriverManager#getConnection(String, String, String)
     */
    public static Connection getConnection(String serverName, int port, String databaseName, String username, String password) throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{serverName});
        dataSource.setPortNumbers(new int[]{port});
        dataSource.setDatabaseName(databaseName);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource.getConnection();
    }
}
