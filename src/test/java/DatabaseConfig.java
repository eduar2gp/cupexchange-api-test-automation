import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ecommerce-prod";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        // Establishes and returns the database connection
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
