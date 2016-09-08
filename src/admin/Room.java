package admin;

/**
 * Represents a room.
 * This can be rented by a tenant.
 * Created by e_voe_000 on 9/1/2016.
 */
public class Room {
    private static int counter = 0;

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    private int id;
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

        this.id = counter++;
        this.size = size;
        this.price = price;
        this.city = city;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods
    ///////////////////////////////////////////////////////////////////////////


    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
