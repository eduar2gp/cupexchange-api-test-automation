package cupexchange;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig{

    private static final Properties properties = new Properties();
    static final String config = "config.properties";

    // Load the properties file when the class is first loaded
    static {
        try (InputStream input = Files.newInputStream(Paths.get("src/test/resources/"+config))) {
            properties.load(input);
        } catch (Exception e) {
            System.err.println("CRITICAL: Failed to load config.properties. Using default connection values.");
        }
    }

    public static Connection getConnection() throws SQLException {
        // Retrieve values from the properties file with defaults as a fallback
        String dbUrl = properties.getProperty("db.url", "");
        String dbUser = properties.getProperty("db.user", "");
        String dbPassword = properties.getProperty("db.password", "");

        // Establishes and returns the database connection
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
