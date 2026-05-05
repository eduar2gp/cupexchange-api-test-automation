package cupexchange;

import com.aventstack.extentreports.Status;
import constants.Endpoints;
import io.restassured.http.ContentType;
import model.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AccountApiTest extends BaseTest{

    @Test
    @Order(5)
    public void addNewAccount() {
        String token = UserVerificationTest.getJwtToken();

        if (token == null) {
            throw new IllegalStateException("JWT Token is null. Ensure the login step runs first.");
        }

        test.set(extent.createTest("addNewAccount"));

        NewAccountRequest request = new NewAccountRequest();
        request.setAccountName(UserVerificationTest.getFullName());
        request.setBaseCurrency("CUP");
        request.setCardNumber("1234567809876543");
        request.setPaymentGatewayId(2); //BPA

        NewAccountResponse response = given()
                .log().all()
                .header("Authorization", "Bearer " + token) // Added JWT Token
                .header("Accept", "application/json")
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.CREATE_ACCOUNT)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(NewAccountResponse.class);

        System.out.println("Account ID: " + response.getId());

        test.get().log(Status.PASS, "addNewAccount test passed successfully.");
    }

}
