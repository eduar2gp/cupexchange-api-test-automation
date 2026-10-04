package cupexchange;

import constants.Endpoints;
import io.restassured.http.ContentType;
import model.PredictionOrderRequest;
import model.UserVerificationTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

public class PredictionMarketOrderApiTest extends BaseTest{

    @Test
    @Order(1)
    public void createPredictionMarketOrder() {
        PredictionOrderRequest request = getPredictionOrderRequest();
        System.out.println(request.toString());

        String response = given()
                .header("Authorization", "Bearer " + UserVerificationTest.getJwtToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.CREATE_PREDICTION_MARKET_ORDER) // Replace with the actual endpoint
                .then()
                .statusCode(201)
                .extract()
                .asString();

        System.out.println(response);
    }

    private static PredictionOrderRequest getPredictionOrderRequest() {
        PredictionOrderRequest request = new PredictionOrderRequest();
        request.setSide(PredictionOrderRequest.OrderSide.BUY);
        request.setOutcomePosition(PredictionOrderRequest.OutcomePosition.YES);
        request.setPrice(new BigDecimal("0.5")); // Replace with a valid price
        request.setQuantity(100); // Replace with a valid quantity
        request.setMarketId(4L); // Replace with a valid market ID
        request.setAppUserId(UserVerificationTest.getUserId());
        request.setWalletId(UserVerificationTest.getCupWalletId());
        return request;
    }
}
