package Admin;

/**
 * Created by e_voe_000 on 9/1/2016.
 */

/*
Landlords can add room to be rented by tenants
 */

public class Room {
    private String roomName;
    private int numberOfBedroom;
    private int numberOfBathroom;
    private int numberOfKitchen;

    public Room(String roomName, int numberOfBedroom, int numberOfBathroom, int numberOfKitchen) {
        //Test Room

        assert roomName != null;
        assert !roomName.isEmpty();
        assert 0 <= numberOfBedroom && numberOfBedroom <= 7;
        assert 0 <= numberOfBathroom && numberOfBathroom <= 5;
        assert 0 <= numberOfKitchen && numberOfKitchen <= 3;

        this.roomName = roomName;
        this.numberOfBedroom = numberOfBedroom;
        this.numberOfBathroom = numberOfBathroom;
        this.numberOfKitchen = numberOfKitchen;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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
