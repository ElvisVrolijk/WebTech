package admin;

/**
 * Represents a room.
 * This can be rented by a tenant.
 * Created by e_voe_000 on 9/1/2016.
 */
public class Room {
    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////
    private int size;
    private int price;
    private String city;

    /**
     * Constructor
     *
     * @param size  Size of the room in square meters.
     * @param price Price of the room.
     * @param city  Location of the room.
     */
    public Room(int size, int price, String city) {
        //Preconditions
        assert city != null : "City can not be null";
        assert !city.isEmpty() : "City name can not be empty";

        this.size = size;
        this.price = price;
        this.city = city;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods
    ///////////////////////////////////////////////////////////////////////////



    public int getSize() {
        return size;
    }

    public int getPrice() {
        return price;
    }

    public String getCity() {
        return city;
    }
}
