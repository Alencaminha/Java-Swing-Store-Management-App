package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private String username, fullName, password, email, accessLevel;

    public User(String username, String fullName, String password, String email, String accessLevel) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.accessLevel = accessLevel;
    }
}
