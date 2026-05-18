package model;

public class UserVerificationTest {
    private static String jwtToken;
    private static Long userId;
    private static String fullName;
    private static Long bpaAccountId;
    private static Long zellerAccountId;

    public static Long getZellerAccountId() {
        return zellerAccountId;
    }

    public static void setZellerAccountId(Long zellerAccountId) {
        UserVerificationTest.zellerAccountId = zellerAccountId;
    }

    // Getter to access token in other classes
    public static String getJwtToken() {
        return jwtToken;
    }

    public static Long getUserId(){
        return userId;
    }

    public static String getUserName(){
        return fullName;
    }

    public static void setJwtToken(String jwtToken) {
        UserVerificationTest.jwtToken = jwtToken;
    }

    public static void setUserId(Long userId) {
        UserVerificationTest.userId = userId;
    }

    public static void setUserName(String fullName){
        UserVerificationTest.fullName = fullName;
    }

    public static void setBpaAccountId(Long bpaAccountId){
        UserVerificationTest.bpaAccountId = bpaAccountId;
    }

    public static Long getBpaAccountId(){
        return UserVerificationTest.bpaAccountId;
    }

}