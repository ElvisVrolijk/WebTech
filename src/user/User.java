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

    private String firstName;
    private String lastName;
    private String username;
    private String password;

    /**
     * Constructor
     */
    public User(String firstName, String lastName, String username, String password) throws RuntimeException {
        //Precondition
        assert firstName != null;
        assert lastName != null;
        assert username != null;
        assert password != null;

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("User credentials can not contain empty value");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
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
