package model;

public class User {
    private final String username, fullName, password, email, accessLevel;

    public User(String username, String fullName, String password, String email, String accessLevel) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.accessLevel = accessLevel;
    }
    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAccessLevel() {
        return accessLevel;
    }
}
