package user;

/**
 * Represents a user.
 * Created by e_voe_000 on 9/1/2016.
 */

/*
Create a user that meets the requirements
 */

// Abstract because I do not want a user instances.
public class User {

    private String fistName;
    private String lastName;
    private String username;
    private String password;

    /**
     * Constructor
     */
    public User(String firstName, String lastName, String username, String password) {
        //Precondition
        assert firstName != null;
        assert lastName != null;
        assert username != null;
        assert password != null;

        this.fistName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFistName() {
        return fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
