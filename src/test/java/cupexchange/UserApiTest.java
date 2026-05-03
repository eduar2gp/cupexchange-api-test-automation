package cupexchange;

import constants.Endpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import model.LoginRequest;
import model.LoginResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import static io.restassured.RestAssured.given;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class UserApiTest {

    static final String config = "config.properties";

    @BeforeAll
    public static void setup() {
        Properties properties = new Properties();
        // 1. Load the properties file from src/test/resources
        try (InputStream input = Files.newInputStream(Paths.get("src/test/resources/"+config))) {
            properties.load(input);
            // 2. Set the base URI dynamically, using a fallback default if property is missing
            RestAssured.baseURI = properties.getProperty("api.url", "");
        } catch (Exception e) {
            System.err.println("CRITICAL: Failed to load configuration file. Using default settings.");
        }
    }

    @Test
    public void verifyUserEndpointAgainstDatabase() {
        String dbEmail = null;
        // 1. Read configuration (e.g., Database and API URLs) from a properties file
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(Paths.get("src/test/resources/"+config))) {
            properties.load(input);
        } catch (Exception e) {
            System.err.println("Failed to load properties file: " + e.getMessage());
        }
        // 2. Query the PostgreSQL database to get the expected value
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT email FROM app_user WHERE id = 4")) {
            if (rs.next()) {
                dbEmail = rs.getString("email");
                System.out.println("Database Email: " + dbEmail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LoginRequest request = new LoginRequest();
        request.setUsername(properties.getProperty("username"));
        request.setPassword(properties.getProperty("password"));

        LoginResponse response = given()
                .log().all()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.LOGIN)
                .then()
                .log().body() // Logs the response body
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        System.out.println("Token: " + response.getJwtToken());
    }
}