package servlet;

import admin.Room;
import user.Landlord;
import user.Tenant;
import user.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * This class is a wrapper for the HttpServlet.
 * The class adds extra functionalities for servlet that
 * handles logged in users.
 * <p>
 * Created by Derwin on 08-Sep-16.
 */
public abstract class AbstractUserServlet extends HttpServlet {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    private ArrayList<Room> rooms;

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();

        //Get reference of the HashMap in context
        HashMap<Landlord, List<Room>> hashMap =
                (HashMap<Landlord, List<Room>>) servletContext.getAttribute("Rooms");

        //Get all the landlords
        Set<Landlord> landlords = hashMap.keySet();
        this.rooms = new ArrayList<>();
        for (Landlord landlord : landlords) { //Go through all landlords
            List<Room> landlordRooms = hashMap.get(landlord);
            this.rooms.addAll(landlordRooms); //Add all rooms to the list
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        //Prepare the response
        response.setStatus(200);
        response.setContentType("text/html");

        authenticationCheck();

        trackUser();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        //Prepare the response
        response.setStatus(200);
        response.setContentType("text/html");

        authenticationCheck();

        trackUser();
    }

    /**
     * Checks if the user is logged in and redirect the
     * user to the correct page according to the user type.
     */
    void authenticationCheck() throws IOException {
        if (!isLoggedIn()) {
            response.sendRedirect("/login.html");
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("User");

            boolean isTenant = user instanceof Tenant;
            boolean isLandlord = user instanceof Landlord;
            boolean usingLandlordServlet = this instanceof LandlordServlet;
            boolean usingTenantServlet = this instanceof TenantServlet;

            if (isTenant && usingLandlordServlet) {
                response.sendRedirect("/tenant");

            } else if (isLandlord && usingTenantServlet) {
                response.sendRedirect("/landlord");
            }
        }
    }

    /**
     * Counts the amount of times the user visits a page
     */
    void trackUser() {
        Cookie[] cookies = request.getCookies(); //Get all cookies
        Cookie trackingCookie = null;
        for (Cookie cookie : cookies) { //Go through all cookies

            //Check whether the cookie has name of the servlet that inherits from this class
            if (cookie.getName().equals(this.getServletName())) {
                trackingCookie = cookie; //Set reference of cookie
            }
        }

        boolean noCookieFound = trackingCookie == null;
        if (noCookieFound) {
            //Add new cookie
            response.addCookie(new Cookie(this.getServletName(), "1"));
        } else {
            //Get the value
            String value = trackingCookie.getValue();
            int count = Integer.parseInt(value);

            //Increment the number
            trackingCookie.setValue(String.valueOf(++count));
            response.addCookie(trackingCookie);
        }
    }

    /**
     * @return Returns true if the user is logged in, otherwise false.
     */
    boolean isLoggedIn() {
        HttpSession session = request.getSession(); //Get session
        return session.getAttribute("User") != null; //Check whether session attribute is null
    }

    /**
     * @return Returns the room specified by the id, otherwise return null if not found.
     */
    Room getRoom(int id) {
        assert id >= 0 : "id can not be negative";

        List<Room> rooms = getRooms();
        for (Room room : rooms) { //Go through all the rooms
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }

    /**
     * @return Returns a list of rooms.
     */
    List<Room> getRooms() {
        return rooms;
    }

    /**
     * @param landlord Landlord that owns the rooms.
     * @return Returns a list of rooms that belong to the landlord specified.
     */
    List<Room> getRooms(Landlord landlord) {
        ServletContext servletContext = getServletContext();

        //Get reference of the HashMap in context
        HashMap<Landlord, List<Room>> hashMap =
                (HashMap<Landlord, List<Room>>) servletContext.getAttribute("Rooms");

        return hashMap.get(landlord);
    }

}