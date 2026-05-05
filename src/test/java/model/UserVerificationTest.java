package model;

public class UserVerificationTest {
    private static String jwtToken;
    private static Long userId;
    private static String fullName;
    // Getter to access token in other classes
    public static String getJwtToken() {
        return jwtToken;
    }

    public static Long getUserId(){
        return userId;
    }

    public static String getFullName(){
        return fullName;
    }

    public static void setJwtToken(String jwtToken) {
        UserVerificationTest.jwtToken = jwtToken;
    }

    public static void setUserId(Long userId) {
        UserVerificationTest.userId = userId;
    }

    public static void setFullName(String fullName){
        UserVerificationTest.fullName = fullName;
    }
}