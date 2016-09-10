package user;

import admin.Room;

/**
 * Represents a tenant user.
 * This user is able to view house that are for renting only.
 * <p>
 * Created by e_voe_000 on 9/1/2016.
 */
public class Tenant extends User {


    private Room room;

    /**
     * Constructor
     */
    public Tenant(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
    }

    /**
     * Rents a room and adds to the field.
     * @param room The room to be rented.
     */
    public void rentRoom(Room room) {
        room.setRented();
        this.room = room;
    }

    /**
     * @return Returns true if the user has rented a room already.
     */
    public boolean hasRented() {
        return room != null;
    }
}
