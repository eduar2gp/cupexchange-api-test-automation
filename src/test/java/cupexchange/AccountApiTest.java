package cupexchange;

import constants.Endpoints;
import io.restassured.http.ContentType;
import model.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.*; // Ensure this import is present
import java.math.BigDecimal;
import static io.restassured.RestAssured.given;

// This ensures the same class instance is used for all tests,
// helping variables persist without needing 'static'
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountApiTest extends BaseTest {

    private long fromAccountId;
    private long toAccountId;
    private long paymentId;

    private static final Faker faker = new Faker();

    // Use a method to get the token to ensure it's fresh
    private String getToken() {
        String token = UserVerificationTest.getJwtToken();
        if (token == null) throw new IllegalStateException("JWT Token is null!");
        return token;
    }

    @Test
    @Order(1)
    public void addNewBpaAccount() {
        AccountRequest request = new AccountRequest();
        request.setAccountName(UserVerificationTest.getUserName());
        request.setBaseCurrency("CUP");
        request.setCardNumber("1234567809876543");
        request.setPaymentGatewayId(BaseTest.bpaGatewayId);

        AccountResponse response = given()
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.CREATE_ACCOUNT)
                .then()
                .statusCode(200)
                .extract().as(AccountResponse.class);

        this.fromAccountId = response.getId();
        UserVerificationTest.setBpaAccountId(response.getId());

        AccountResponse[] accounts = given()
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get(Endpoints.ACTIVE_ACCOUNTS + "BPA")
                .then()
                .statusCode(200)
                .extract().as(AccountResponse[].class);

        this.toAccountId = accounts[0].getId();
    }

    @Test
    @Order(2)
    public void bpaPayment() {
        // Stop the test if the previous ID wasn't captured
        Assumptions.assumeTrue(fromAccountId > 0, "From Account ID is missing!");

        PaymentRequest request = new PaymentRequest();
        request.setRequestType("DEPOSIT");
        request.setMethod("BANK");
        request.setAmount(100000L);
        request.setFromAccountId(fromAccountId);
        request.setToAccountId(toAccountId);

        PaymentResponse response = given()
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.ADD_PAYMENT)
                .then()
                .statusCode(201)
                .extract().as(PaymentResponse.class);

        this.paymentId = response.getId();
    }

    @Test
    @Order(3)
    public void bpaDeposit() {
        Assumptions.assumeTrue(paymentId > 0, "Payment ID is missing!");

        DepositRequest request = new DepositRequest();
        request.setType("DEPOSIT");
        request.setAmount(new BigDecimal("100000"));
        request.setCurrencyCode("CUP");
        request.setReferenceId(String.valueOf(paymentId));

        given()
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.DEPOSTI) // Check if this should be DEPOSIT
                .then()
                .statusCode(202);
    }

    @Test
    @Order(4)
    public void addZelleAccount() {
        AccountRequest request = new AccountRequest();
        request.setAccountName(UserVerificationTest.getUserName());
        request.setBaseCurrency("USD");
        request.setEmail(faker.internet().emailAddress());
        request.setPaymentGatewayId(BaseTest.zelleGatewayId);
        request.setPhone(faker.phoneNumber().cellPhone());

        AccountResponse response = given()
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.CREATE_ACCOUNT)
                .then()
                .statusCode(200)
                .extract().as(AccountResponse.class);

        this.fromAccountId = response.getId();
        UserVerificationTest.setZellerAccountId(response.getId());

        AccountResponse[] accounts = given()
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get(Endpoints.ACTIVE_ACCOUNTS + "ZELLE")
                .then()
                .statusCode(200)
                .extract().as(AccountResponse[].class);

        this.toAccountId = accounts[0].getId();
    }

    @Test
    @Order(5)
    public void zellePayment() {
        // Stop the test if the previous ID wasn't captured
        Assumptions.assumeTrue(fromAccountId > 0, "From Account ID is missing!");

        PaymentRequest request = new PaymentRequest();
        request.setRequestType("DEPOSIT");
        request.setMethod("BANK");
        request.setAmount(1000L);
        request.setFromAccountId(fromAccountId);
        request.setToAccountId(toAccountId);

        PaymentResponse response = given()
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.ADD_PAYMENT)
                .then()
                .statusCode(201)
                .extract().as(PaymentResponse.class);

        this.paymentId = response.getId();
    }

    @Test
    @Order(6)
    public void zelleDeposit() {
        Assumptions.assumeTrue(paymentId > 0, "Payment ID is missing!");

        DepositRequest request = new DepositRequest();
        request.setType("DEPOSIT");
        request.setAmount(new BigDecimal("1000"));
        request.setCurrencyCode("USD");
        request.setReferenceId(String.valueOf(paymentId));

        given()
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.DEPOSTI) // Check if this should be DEPOSIT
                .then()
                .statusCode(202);
    }
}