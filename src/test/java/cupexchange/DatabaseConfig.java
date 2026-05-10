package cupexchange;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig extends BaseTest{

    public static Connection getConnection() throws SQLException {
        // Retrieve values from the properties file with defaults as a fallback
        String dbUrl = properties.getProperty("db.url", "");
        String dbUser = properties.getProperty("db.user", "");
        String dbPassword = properties.getProperty("db.password", "");

        // Establishes and returns the database connection
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
