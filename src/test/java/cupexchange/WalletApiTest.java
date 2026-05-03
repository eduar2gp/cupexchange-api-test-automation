package cupexchange;

import com.aventstack.extentreports.Status;
import constants.Endpoints;
import io.restassured.http.ContentType;
import model.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class WalletApiTest extends BaseTest {

    @Test
    @Order(3)
    public void createUsdCupWallet() {
        test.set(extent.createTest("createUsdCupWallet"));
        // 1. Retrieve the token saved from the previous login/registration step
        // (Ensure your UserVerificationTest saves the token to a public static variable)
        String token = UserVerificationTest.getJwtToken();

        if (token == null) {
            throw new IllegalStateException("JWT Token is null. Ensure the login step runs first.");
        }

        NewWalletRequest newWalletRequest = new NewWalletRequest();
        newWalletRequest.setCurrencyCode("USD");

        // 2. Execute the POST request with the Authorization header
        NewWalletResponse response = given()
                .log().all()
                .header("Authorization", "Bearer " + token) // Added JWT Token
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .body(newWalletRequest)
                .when()
                .post(Endpoints.CREATE_WALLET)
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(NewWalletResponse.class);

        System.out.println("Currency Code: " + response.getCurrencyCode());
        newWalletRequest.setCurrencyCode("CUP");

        response = given()
                .log().all()
                .header("Authorization", "Bearer " + token) // Added JWT Token
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .body(newWalletRequest)
                .when()
                .post(Endpoints.CREATE_WALLET)
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(NewWalletResponse.class);

        System.out.println("Currency Code: " + response.getCurrencyCode());
        test.get().log(Status.PASS, "Create New Wallet test passed successfully.");
    }
}