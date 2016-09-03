package admin;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.MalformedJsonException;
import exception.DuplicateUserException;
import exception.UserNotFoundException;
import user.Landlord;
import user.Tenant;
import user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Handles the database system.
 * This contains all the user information.
 * Created by Derwin on 31-Aug-16.
 * // TODO: 03-Sep-16 Extra functionality: remove room
 * // TODO: 03-Sep-16 Extra functionality: modify room
 * // TODO: 03-Sep-16 Statistical Data. Visits to pages should be logged according to the user who visited.
 */
public class Database {

    ///////////////////////////////////////////////////////////////////////////
    // Constant
    ///////////////////////////////////////////////////////////////////////////
    private static final String DATABASE_FILE = "user.json";

    private static Database instance;

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////
    private List<User> users = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();

    /**
     * @return The instance of this database.
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Constructor
     */
    private Database() {
        //no instance
        loadData();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Adds the user to the database.
     * <p>
     * Pre-condition:
     * -The username should be unique, otherwise decline.
     *
     * @param user
     * @throws DuplicateUserException if the user is already registered.
     */
    public void addUser(User user) throws DuplicateUserException {
        assert user != null : "User can not be null";

        if (userExists(user)) {
            throw new DuplicateUserException("Username already exist in database.");
        }

        users.add(user); //Add to the list
        updateData(); //Update the json file
    }

    /**
     * @return the user based on the username.
     * @throws UserNotFoundException if the user was not found in database.
     */
    public User getUserByUsername(String username) throws UserNotFoundException {
        assert username != null : "Username can not be null";

        for (User user : users) { //Go through all users
            String registeredUsername = user.getUsername();
            if (username.equals(registeredUsername)) {
                return user;
            }

        }
        throw new UserNotFoundException("User does not exist.");
    }

    /**
     * Adds the room given in as the argument.
     * @param room The room to be added.
     */
    public void addRoom(Room room) {
        assert room != null : "Room can not be null";
        rooms.add(room);
    }

    /**
     * Finds a room based on the criteria given.
     * @param size The size of the room in square meters.
     * @param price The maximum price of the room.
     * @param city The location of the room.
     * @return Returns the room if it was found, otherwise null.
     */
    public Room getRoomByCriteria(int size, int price, String city) {
        assert city != null : "City can not be null";
        assert !city.isEmpty() : "City name can not be empty";

        for (Room room : rooms) {
            int roomSize = room.getSize();
            int roomPrice = room.getPrice();
            String roomCity = room.getCity();

            if (roomSize == size && roomPrice <= price && roomCity.equals(city)) {
                //Found room
                return room;
            }
        }
        return null;
    }

    /**
     * @param newUser the user to check.
     * @return whether user exist in the database.
     */
    private boolean userExists(User newUser) {
        for (User registeredUser : users) {
            String registeredUsername = registeredUser.getUsername();
            String newUsername = newUser.getUsername();
            if (registeredUsername.equals(newUsername)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reads data from file.
     */
    private void loadData() {
        try {
            File file = new File(DATABASE_FILE);
            if (!file.exists()) {
                //File doesn't exist
                file.createNewFile();
            }
            //Read data
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            String data = builder.toString();

            //Deserialize
            List<User> users = deserializeUsers(data);
            if (users != null) {
                this.users = deserializeUsers(data);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the file with new data.
     */
    private void updateData() {
        try {
            FileWriter fileWriter = new FileWriter(DATABASE_FILE);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String json = serializeUsers(users);
            bufferedWriter.write(json);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Converts the user array to a json String.
     *
     * @return The json in String
     */
    private String serializeUsers(List<User> users) {
        assert users != null : "Users should not be null";

        //Precondition
        if (users.size() == 0) {
            return null;
        }
        Gson gson = new Gson();

        //This is a simple solution to avoid instantiating abstract object from json
        //In this case the users is split into two list, one being tenants and the other being landlord.
        //These 2 list will be later on exported to the data.json file in a single file.
        List<Tenant> tenants = new ArrayList<>();
        List<Landlord> landlords = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Tenant) {
                tenants.add((Tenant) user);
            } else if (user instanceof Landlord) {
                landlords.add((Landlord) user);
            }
        }
        Object[] objects = new Object[2];
        objects[0] = tenants;
        objects[1] = landlords;
        return gson.toJson(objects);
    }

    /**
     * Converts string to actual java object.
     *
     * @return The list of users from json String.
     */
    private List<User> deserializeUsers(String json) throws MalformedJsonException {
        assert json != null : "JSON is null";

        //Precondition
        if (json.length() == 0) {
            return null;
        }
        Gson gson = new Gson();
        Object[] objects = gson.fromJson(json, Object[].class);
        Object tenants = objects[0];
        Object landlords = objects[1];

        ArrayList<User> users = new ArrayList<>();

        //This is not the best solution, but works so far.
        //Can still be improved in upcoming versions.
        if (tenants instanceof List && landlords instanceof List) {
            //Go through all tenants
            for (int i = 0; i < ((List) tenants).size(); i++) {
                Object tenant = ((List) tenants).get(i);
                if (tenant instanceof LinkedTreeMap) {
                    Collection values = ((LinkedTreeMap) tenant).values();
                    Object[] properties = values.toArray();
                    String firstName = (String) properties[0];
                    String lastName = (String) properties[1];
                    String username = (String) properties[2];
                    String password = (String) properties[3];
                    users.add(new Tenant(firstName, lastName, username, password));
                }
            }

            //Go through all landlords
            for (int i = 0; i < ((List) landlords).size(); i++) {
                Object landlord = ((List) landlords).get(i);
                if (landlord instanceof LinkedTreeMap) {
                    Collection values = ((LinkedTreeMap) landlord).values();
                    Object[] properties = values.toArray();
                    String firstName = (String) properties[0];
                    String lastName = (String) properties[0];
                    String username = (String) properties[0];
                    String password = (String) properties[0];
                    users.add(new Landlord(firstName, lastName, username, password));
                }
            }
        } else {
            //Database file is corrupted
            throw new MalformedJsonException("Database json file is malformed.");
        }
        return users;
    }
}
