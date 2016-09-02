package Users;

/**
 * Created by e_voe_000 on 9/1/2016.
 */

/*
Create a user that meets the requirements
 */

// Abstract because I do not want a user instances.
public abstract class User  {
    private String username;
    private String password;

    public User(String username, String password) {
        /*
        Test User
         */
        assert username != null;
        assert !username.isEmpty();
        assert password != null;
        assert !password.isEmpty();

        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
