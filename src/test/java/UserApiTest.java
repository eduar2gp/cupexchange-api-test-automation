
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class UserApiTest {

    @BeforeAll
    public static void setup() {
        Properties properties = new Properties();

        // 1. Load the properties file from src/test/resources
        try (InputStream input = Files.newInputStream(Paths.get("src/test/resources/config.properties"))) {
            properties.load(input);

            // 2. Set the base URI dynamically, using a fallback default if property is missing
            RestAssured.baseURI = properties.getProperty("api.url", "http://localhost:8080");

            // Optional: Set other global configurations
            RestAssured.port = Integer.parseInt(properties.getProperty("api.port", "8080"));
        } catch (Exception e) {
            System.err.println("CRITICAL: Failed to load configuration file. Using default settings.");
            RestAssured.baseURI = "http://localhost:8080"; // Default fallback
        }
    }

    @Test
    public void verifyUserEndpointAgainstDatabase() {
        String dbEmail = null;
        String apiUrl = "";

        // 1. Read configuration (e.g., Database and API URLs) from a properties file
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(Paths.get("src/test/resources/config.properties"))) {
            properties.load(input);
            // Example: String dbUrl = properties.getProperty("db.url");
            apiUrl = properties.getProperty("api.url", "http://localhost:8080"); // Fallback URL
        } catch (Exception e) {
            System.err.println("Failed to load properties file: " + e.getMessage());
        }

        // 2. Query the PostgreSQL database to get the expected value
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT email FROM app_user WHERE id = 3")) {

            if (rs.next()) {
                dbEmail = rs.getString("email");
                System.out.println("Database Email: " + dbEmail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. Define the POST request payload
        String jsonPayload = "{\n" +
                "  \"username\": \"tester0\",\n" +
                "  \"password\": \"123\"\n" +
                "}";

        // 4. Trigger the REST API and validate the response against the database value
        given()
                .contentType(ContentType.JSON)
                .body(jsonPayload) // Attach the JSON payload to the POST request
                .when()
                .post(apiUrl + "/api/v1/auth/login") // Use the URL read from properties
                .then()
                .statusCode(200)
                .body("email", equalTo(dbEmail)); // Asserts API data against DB result
    }
}
