package cupexchange;

import constants.DepositConfiguration;
import constants.Endpoints;
import io.restassured.http.ContentType;
import model.ExchangeOrderRequest;
import model.UserVerificationTest;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExchangeMarketOrderApiTest extends BaseTest{

    @Test
    @Order(1)
    public void createMarketBuyExchangeOrder() {
        ExchangeOrderRequest request = new ExchangeOrderRequest();
        request.setSide("BUY");
        request.setPairCode("USDCUP");
        request.setType("MARKET");
        request.setVolume(DepositConfiguration.getMarketOrderVolume());
        request.setUsername(UserVerificationTest.getUserName().toLowerCase());

        String response = given()
                .header("Authorization", "Bearer " + UserVerificationTest.getJwtToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.CREATE_EXCHANGE_ORDER)
                .then()
                .statusCode(201)
                .extract()
                .asString();

        System.out.println(response);
    }

    @Test
    @Order(2)
    public void createMarketSellExchangeOrder() {
        ExchangeOrderRequest request = new ExchangeOrderRequest();
        request.setSide("SELL");
        request.setPairCode("USDCUP");
        request.setType("MARKET");
        request.setVolume(DepositConfiguration.getMarketOrderVolume());
        request.setUsername(UserVerificationTest.getUserName().toLowerCase());

        String response = given()
                .header("Authorization", "Bearer " + UserVerificationTest.getJwtToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.CREATE_EXCHANGE_ORDER)
                .then()
                .statusCode(201)
                .extract()
                .asString();

        System.out.println(response);
    }

}