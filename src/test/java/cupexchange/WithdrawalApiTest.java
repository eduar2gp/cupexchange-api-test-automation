package cupexchange;

import constants.Endpoints;
import io.restassured.http.ContentType;
import model.PaymentRequest;
import model.PaymentResponse;
import model.UserVerificationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class WithdrawalApiTest extends BaseTest{

    // Use a method to get the token to ensure it's fresh
    private String getToken() {
        String token = UserVerificationTest.getJwtToken();
        if (token == null) throw new IllegalStateException("JWT Token is null!");
        return token;
    }

    @Test
    @Order(1)
    public void createBpaWithdrawal() {
        PaymentRequest request = new PaymentRequest();
        request.setToAccountId(UserVerificationTest.getBpaAccountId());
        request.setMethod("BANK");
        request.setRequestType("WITHDRAWAL");
        request.setAmount(50);

        // 1. Extract as an Array
        PaymentResponse[] responses = given()
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.ADD_PAYMENT)
                .then()
                .statusCode(201)
                .extract().as(PaymentResponse[].class);

        // 2. Validate and Store the ID from the first element
        Assertions.assertTrue(responses.length > 0, "Response array is empty!");
    }

    @Test
    @Order(2)
    public void createZelleWithdrawal() {
        PaymentRequest request = new PaymentRequest();
        request.setToAccountId(UserVerificationTest.getZellerAccountId());
        request.setMethod("BANK");
        request.setRequestType("WITHDRAWAL");
        request.setAmount(50);

        // 1. Extract as an Array
        PaymentResponse[] responses = given()
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.ADD_PAYMENT)
                .then()
                .statusCode(201)
                .extract().as(PaymentResponse[].class);

        // 2. Validate and Store the ID from the first element
        Assertions.assertTrue(responses.length > 0, "Response array is empty!");
    }
}