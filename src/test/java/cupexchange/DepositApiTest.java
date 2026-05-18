package cupexchange;

import constants.Endpoints;
import io.restassured.http.ContentType;
import model.*;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepositApiTest extends BaseTest {

//    static String token = UserVerificationTest.getJwtToken();

//    @Test
//    @Order(4)
//    public void depositCupCash() {
////        String token = UserVerificationTest.getJwtToken();
//
//        if (token == null) {
//            throw new IllegalStateException("JWT Token is null. Ensure the login step runs first.");
//        }
//
//        CashOrderDepositRequest request = new CashOrderDepositRequest();
//        request.setUserId(UserVerificationTest.getUserId());
//        request.setProviderId(7L);
//        request.setAmount(1000);
//        request.setCurrencyCode("CUP");
//        request.setType("DEPOSIT");
//
//        DepositResponse response = given()
//                .log().all()
//                .header("Authorization", "Bearer " + token) // Added JWT Token
//                .header("Accept", "application/json")
//                .contentType(ContentType.JSON)
//                .body(request)
//                .when()
//                .post(Endpoints.DEPOSIT_CASH_ORDER)
//                .then()
//                .log().all()
//                .statusCode(201)
//                .extract()
//                .as(DepositResponse.class);
//
//        System.out.println("Created At:" + response.getCreatedAt());
//
//    }


    private String authToken;
    private long pendingDepositId;

    @BeforeAll
    public void loginAndSetup() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("manager");
        loginRequest.setPassword("12345678");

        LoginResponse loginResponse = given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post(Endpoints.LOGIN)
                .then()
                .statusCode(200)
                .extract().as(LoginResponse.class);

        this.authToken = loginResponse.getJwtToken();
    }

    @Test
    @Order(1)
    public void verifyPendingDeposit() {
        PendingDepositResponse response = given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get(Endpoints.GET_PENDING_DEPOSITS)
                .then()
                .statusCode(200)
                .extract().as(PendingDepositResponse.class);

        // Capture the first deposit to process it in the next test
        PendingDeposit myDeposit = response.getContent().stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Deposit not found in pending list!"));

        this.pendingDepositId = myDeposit.getId();
        System.out.println("Captured Deposit ID for approval: " + pendingDepositId);
    }

    @Test
    @Order(2)
    public void approveBpaDeposit() {
        // Guard check: ensure we actually have an ID to approve
        Assumptions.assumeTrue(pendingDepositId > 0, "Pending Deposit ID was not captured in previous step!");

        ApproveDepositRequest request = new ApproveDepositRequest();
        request.setTransactionId(pendingDepositId);
        request.setAction("CONFIRM_DEPOSIT");
        request.setReason("Verified by automation");

        given()
                .header("Authorization", "Bearer " + authToken)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.PROCESS_TRANSACTION)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    public void verifySecondPendingDeposit() {
        PendingDepositResponse response = given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get(Endpoints.GET_PENDING_DEPOSITS)
                .then()
                .statusCode(200)
                .extract().as(PendingDepositResponse.class);

        // Capture the first deposit to process it in the next test
        PendingDeposit myDeposit = response.getContent().stream()
                .toList().get(1);

        this.pendingDepositId = myDeposit.getId();
        System.out.println("Captured Deposit ID for approval: " + pendingDepositId);
    }

    @Test
    @Order(4)
    public void approveSecondDeposit() {
        // Guard check: ensure we actually have an ID to approve
        Assumptions.assumeTrue(pendingDepositId > 0, "Pending Deposit ID was not captured in previous step!");

        ApproveDepositRequest request = new ApproveDepositRequest();
        request.setTransactionId(pendingDepositId);
        request.setAction("CONFIRM_DEPOSIT");
        request.setReason("Verified by automation");

        given()
                .header("Authorization", "Bearer " + authToken)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(Endpoints.PROCESS_TRANSACTION)
                .then()
                .statusCode(200);
    }
}