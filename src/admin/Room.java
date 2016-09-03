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
    private String name;
    private int numberOfBedroom;
    private int numberOfBathroom;
    private int numberOfKitchen;

    /**
     * Constructor
     */
    public Room(String name, int numberOfBedroom, int numberOfBathroom, int numberOfKitchen) {
        //Preconditions
        assert name != null;
        assert !name.isEmpty();
        assert 0 <= numberOfBedroom && numberOfBedroom <= 7;
        assert 0 <= numberOfBathroom && numberOfBathroom <= 5;
        assert 0 <= numberOfKitchen && numberOfKitchen <= 3;

        this.name = name;
        this.numberOfBedroom = numberOfBedroom;
        this.numberOfBathroom = numberOfBathroom;
        this.numberOfKitchen = numberOfKitchen;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods
    ///////////////////////////////////////////////////////////////////////////
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfBedroom() {
        return numberOfBedroom;
    }

    public void setNumberOfBedroom(int numberOfBedroom) {
        this.numberOfBedroom = numberOfBedroom;
    }

    public int getNumberOfBathroom() {
        return numberOfBathroom;
    }

    public void setNumberOfBathroom(int numberOfBathroom) {
        this.numberOfBathroom = numberOfBathroom;
    }

    public int getNumberOfKitchen() {
        return numberOfKitchen;
    }

    public void setNumberOfKitchen(int numberOfKitchen) {
        this.numberOfKitchen = numberOfKitchen;
    }
}
