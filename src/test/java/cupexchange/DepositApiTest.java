package cupexchange;

import com.aventstack.extentreports.Status;
import constants.Endpoints;
import io.restassured.http.ContentType;
import model.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DepositApiTest extends BaseTest{

    @Test
    @Order(4)
    public void depositCupCash() {
        String token = UserVerificationTest.getJwtToken();

        if (token == null) {
            throw new IllegalStateException("JWT Token is null. Ensure the login step runs first.");
        }

        test.set(extent.createTest("deposit"));

        DepositRequest request = new DepositRequest();
        request.setUserId(UserVerificationTest.getUserId());
        request.setProviderId(7L);
        request.setAmount(1000);
        request.setCurrencyCode("CUP");
        request.setType("DEPOSIT");


        // 3. Execute Login
        DepositResponse response = given()
                .log().all()
                .header("Authorization", "Bearer " + token) // Added JWT Token
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.DEPOSIT_CASH_ORDER)
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(DepositResponse.class);

        System.out.println("Created At:" + response.getCreatedAt());

        test.get().log(Status.PASS, "Deposit test passed successfully.");
    }

}