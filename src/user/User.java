package user;

/**
 * Represents a user.
 * <p>
 * Created by e_voe_000 on 9/1/2016.
 */

public abstract class User {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    /**
     * Constructor
     */
    User(String firstName, String lastName, String username, String password) throws RuntimeException {
        //Precondition
        assert firstName != null;
        assert lastName != null;
        assert username != null;
        assert password != null;

        boolean emptyFirstName = firstName.isEmpty();
        boolean emptyLastName = lastName.isEmpty();
        boolean emptyUsername = username.isEmpty();
        boolean emptyPassword = password.isEmpty();

        if (emptyFirstName || emptyLastName || emptyUsername || emptyPassword) {
            throw new IllegalArgumentException("One or more credential is empty");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods
    ///////////////////////////////////////////////////////////////////////////

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
