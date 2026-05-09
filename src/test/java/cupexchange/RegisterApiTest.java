package cupexchange;

import constants.Endpoints;
import io.restassured.http.ContentType;
import model.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import util.SimpleDataGenerator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static io.restassured.RestAssured.given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterApiTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(RegisterApiTest.class);

    private static String generatedUsername;
    private static String generatedPassword;
    private static final Faker faker = new Faker();

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
        try (Connection conn = DatabaseConfig.getConnection()) {
            // 1. Verify the connection is active and reachable (3-second timeout)
            if (conn != null && conn.isValid(3)) {

                Statement debugStmt = conn.createStatement();
                ResultSet rsDebug = debugStmt.executeQuery("SELECT count(*) FROM public.verificationtoken");
                if (rsDebug.next()) {
                    System.out.println("Total rows found: " + rsDebug.getInt(1));
                }
                else{
                    System.out.println("Empty verificationtoken table");
                }

                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT * FROM public.verificationtoken ORDER BY expirydate DESC FETCH FIRST 1 ROWS ONLY")) {

                    if (rs.next()) {
                        token = rs.getString("token");
                        System.out.println("there is data");
                        logger.info("Successfully retrieved token: {}", token);
                    } else {
                        System.out.println("no more data found");
                    }
                }
            } else {
                logger.error("Connection is null or invalid before query execution.");
            }
        } catch (Exception e) {
            logger.error("Failed to retrieve verification token from the database", e);
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

        UserVerificationTest.setJwtToken(response.getJwtToken());
        UserVerificationTest.setUserId(response.getId());
        UserVerificationTest.setUserName(request.getUsername());
        System.out.println("Token: " + response.getJwtToken());
    }
}