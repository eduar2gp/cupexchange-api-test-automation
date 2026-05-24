package cupexchange;

import constants.Endpoints;
import constants.DepositConfiguration;
import io.restassured.http.ContentType;
import model.ExchangeOrderRequest;
import model.UserVerificationTest;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExchangeLimitOrderApiTest extends BaseTest{

    @Test
    @Order(1)
    public void createLimitBuyExchangeOrder(){
        ExchangeOrderRequest request = new ExchangeOrderRequest();
        request.setSide("BUY");
        request.setPairCode("USDCUP");
        request.setType("LIMIT");
        request.setVolume(DepositConfiguration.getCupLimitOrderRandomVolume());
        request.setPrice(DepositConfiguration.getCupLimitBuyPrice());
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
    public void createLimitSellExchangeOrder(){
        ExchangeOrderRequest request = new ExchangeOrderRequest();
        request.setSide("SELL");
        request.setPairCode("USDCUP");
        request.setType("LIMIT");
        request.setVolume(DepositConfiguration.getUsdLimitOrderVolume());
        request.setPrice(DepositConfiguration.getUsdLimitSellPrice());
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