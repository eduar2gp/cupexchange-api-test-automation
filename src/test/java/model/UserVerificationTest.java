package model;

public class UserVerificationTest {
    private static String jwtToken;

    // Getter to access token in other classes
    public static String getJwtToken() {
        return jwtToken;
    }

    public static void setJwtToken(String jwtToken) {
        UserVerificationTest.jwtToken = jwtToken;
    }
}