package servlet;

import admin.Room;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.omg.CORBA.RepositoryIdHelper;
import org.w3c.dom.css.Counter;
import user.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a wrapper for the HttpServer.
 * The class adds extra functionalities for servlet that
 * handles logged in users.
 * <p>
 * Created by Derwin on 08-Sep-16.
 */
public abstract class AbstractUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        trackUser(request, response);
        checkUser(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        trackUser(request, response);
        checkUser(request, response);
    }

    void trackUser(HttpServletRequest request, HttpServletResponse response) {
        //Find the cookie
        Cookie[] cookies = request.getCookies();
        Cookie trackingCookie = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(this.getServletName())) {
                trackingCookie = cookie;
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
     * Checks user properties.
     * - Whether the user is logged in.
     * - Redirect the user to the proper page dependant on the user's type.
     */
    void checkUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setContentType("text/html");

        HttpSession session = request.getSession();
        if (!isLoggedIn(session)) { //Check whether the user is logged in
            //User is NOT logged in
            response.sendRedirect("/login.html");
        }
    }

    /**
     * @return Returns true if the user is logged in, otherwise false.
     */
    boolean isLoggedIn(HttpSession session) {
        assert session != null : "Session is null";

        User user = (User) session.getAttribute("User");
        return user != null;
    }

    /**
     * @return Returns the room specified by the id, otherwise return null if not found.
     */
    Room getRoom(int id) {
        List<Room> rooms = getRooms();
        for (Room room : rooms) {
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
        ServletContext servletContext = getServletContext();
        return (List<Room>) servletContext.getAttribute("Rooms");
    }
}
