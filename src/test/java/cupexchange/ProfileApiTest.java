package cupexchange;

import constants.Endpoints;
import io.restassured.http.ContentType;
import model.ProfileRequest;
import model.UserVerificationTest;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ProfileApiTest extends BaseTest {

    private static final Faker faker = new Faker();

    @Test
    public void updateProfile() {
        // Payload solicitado
        ProfileRequest profile = new ProfileRequest();
        profile.setFirstName(faker.name().firstName());
        profile.setMiddleName(faker.name().firstName());
        profile.setLastName(faker.name().lastName());
        profile.setPhone(faker.phoneNumber().cellPhone());
        profile.setAddress(faker.address().fullAddress());
        profile.setCountryCode("CU");
        profile.setProvinceId(1);
        profile.setMunicipalityId(1);

        String token = UserVerificationTest.getJwtToken(); // se asume que esta utilidad existe y retorna JWT válido

        String response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(profile)
                .when()
                .post(Endpoints.EDIT_PROFILE)
                .then()
                // Ajusta el status code esperado si tu API devuelve otro (200/201/204)
                .statusCode(202)
                .extract()
                .asString();

        System.out.println("EDIT_PROFILE response: " + response);
    }
}