package Admin;

import Users.Landlord;
import Users.Tenant;
import Users.User;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;

/**
 * Created by e_voe_000 on 9/1/2016.
 */

/*
Admin takes care of all action that a landlord and tenant can do.
 */
public class Admin {
    private String adminName;

    private final ArrayList<User> users;
    private final ArrayList<Room> rooms;

    public Admin(String adminName) {
        //Test Admin

        assert adminName != null;
        assert !adminName.isEmpty();

        this.adminName = adminName;

        this.users = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    public void registerLandlord(String username, String password) {
        //create a landlord
        Landlord landlord = new Landlord(username, password);
        // add him/ her in the list
        addUser(landlord);
    }

    public void registerTenant(String username, String password) {
        //create a tenant
        Tenant tenant = new Tenant(username, password);
        //add him/ her in the list
        addUser(tenant);
    }

    public void addUser(User user) {
        //test user
        assert user != null;
        //add the user to the list
        users.add(user);
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
