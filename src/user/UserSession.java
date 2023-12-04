package user;
// UserSession.java
public class UserSession {
    private static boolean loggedIn = false;

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean value) {
        loggedIn = value;
    }
}
