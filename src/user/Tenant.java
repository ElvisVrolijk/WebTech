package user;

/**
 * Represents a tenant user.
 * This user is able to view house that are for renting only.
 *
 * Created by e_voe_000 on 9/1/2016.
 */
public class Tenant extends User {

    /**
     * Constructor
     */
    public Tenant(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
    }

}
