package cupexchange;

import constants.Endpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import model.LoginRequest;
import model.LoginResponse;
import model.RegisterRequest;
import model.RegisterResponse;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import util.SimpleDataGenerator;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class RegisterApiTest {

    private static String generatedUsername;
    private static String generatedPassword;
    private static final Faker faker = new Faker();
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
    @Order(1)
    public void registerAndVerifyUser() {
        String token = null;
        // 2. Generate random credentials and save them for the next test
        generatedUsername = faker.name().firstName() + SimpleDataGenerator.getRandomString(3);
        generatedPassword = SimpleDataGenerator.getRandomString(8);

        RegisterRequest request = new RegisterRequest();
        request.setUsername(generatedUsername);
        request.setPassword(generatedPassword);
        request.setEmail(faker.internet().emailAddress());

        // 3. Register the User
        RegisterResponse response = given()
                .log().all()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.REGISTER)
                .then()
                .log().body()
                .statusCode(201)
                .extract()
                .as(RegisterResponse.class);

        // 4. Retrieve Verification Token from DB
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM verificationtoken ORDER BY expirydate DESC FETCH FIRST 1 ROWS ONLY")) {
            if (rs.next()) {
                token = rs.getString("token");
                System.out.println("Token: " + token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (token == null) {
            throw new RuntimeException("Verification token could not be retrieved from the database.");
        }

        // 5. Send GET request to verify the user
        given()
                .log().all()
                .when()
                .get(Endpoints.VERIFY + token)
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    @Order(2)
    public void loginNewUser() {
        // 2. Build the Login Request using the saved fields from Test 1
        LoginRequest request = new LoginRequest();
        request.setUsername(generatedUsername);
        request.setPassword(generatedPassword);

        // 3. Execute Login
        LoginResponse response = given()
                .log().all()
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.LOGIN)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        System.out.println("Token: " + response.getJwtToken());
    }
}