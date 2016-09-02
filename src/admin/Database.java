package admin;

import com.google.gson.Gson;
import exception.DuplicateUserException;
import exception.UserNotFoundException;
import user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handles the database system.
 * This contains all the user information.
 * Created by Derwin on 31-Aug-16.
 */
public class Database {

    ///////////////////////////////////////////////////////////////////////////
    // Constant
    ///////////////////////////////////////////////////////////////////////////
    private static final String DATABASE_FILE = "data.json";

    private static Database instance;

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////
    private List<User> users = new ArrayList<>();

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
        for (User user : users) { //Go through all users
            String registeredUsername = user.getUsername();
            if (username.equals(registeredUsername)) {
                return user;
            }

        }
        throw new UserNotFoundException("User does not exist.");
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
        //Precondition
        if (users.size() == 0) {
            return null;
        }

        Gson gson = new Gson();
        return gson.toJson(users);
    }

    /**
     * Converts string to actual java object.
     *
     * @return The list of users from json String.
     */
    private List<User> deserializeUsers(String json) {
        //Precondition
        if (json.length() == 0) {
            return null;
        }

        Gson gson = new Gson();
        User[] userObjects = gson.fromJson(json, User[].class);


        ArrayList<User> users = new ArrayList<>();
        Collections.addAll(users, userObjects);
        return users;
    }
}
