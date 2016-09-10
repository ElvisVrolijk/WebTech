package servlet;

import admin.Room;
import user.Landlord;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Handles request for the landlord page.
 * <p>
 * Created by Derwin on 06-Sep-16.
 */
@WebServlet("/landlord")
public class LandlordServlet extends AbstractUserServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response); //Checks whether the user is logged in

        List<Room> rooms = getRooms();
        PrintWriter writer = response.getWriter();

        if (rooms.size() == 0) {
            //In case there is no room
            writer.println("<form method=\"post\">\n" +
                    "    <table>\n" +
                    "        <tr><td>No rooms to show</td></tr>\n" +
                    "        <tr><td><button type=\"submit\" name=\"add_room\">Add</button></td><td><button type=\"button\" onclick=\"window.location.href='/logout'\">Logout</button></td></tr>\n" +
                    "    </table>\n" +
                    "</form>");
            return;
        }

        //Dynamic HTML
        writer.println("<form method='post'>");
        writer.println("<table>");
        writer.println("<tr>");
        writer.println("<th>City</th>");
        writer.println("<th>Size</th>");
        writer.println("<th>Price</th>");
        writer.println("</tr>");
        for (Room room : rooms) {
            writer.println("<tr>");
            writer.println("<td>" + room.getCity() + "</td>");
            writer.println("<td>" + room.getSize() + "</td>");
            writer.println("<td>" + room.getPrice() + "</td>");
            writer.println("<td><button type='submit' name='modify_room' value='" + room.getId() + "'>Modify</button></td>");
            writer.println("<td><button type='submit' name='delete_room' value='" + room.getId() + "'>Delete</button></td>");
            writer.println("</tr>");
        }
        writer.println("<tr><td><button type='submit' name='add_room' value='0'>Add</button></td>" +
                "<td><button type='button' onclick='location.href=\"logout\"'>Logout</button></td></tr>");
        writer.println("</table>");
        writer.println("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response); //Checks whether the user is logged in

        boolean isDeleteRoom = request.getParameter("delete_room") != null;
        boolean isModifyRoom = request.getParameter("modify_room") != null;
        boolean isSaveRoom = request.getParameter("save_room") != null;
        boolean isAddRoom = request.getParameter("add_room") != null;

        if (isDeleteRoom) {
            //Delete
            String roomId = request.getParameter("delete_room"); //Get room id
            deleteRoom(roomId); //Process the removal of room
            response.sendRedirect("/landlord"); //Redirect
        } else if (isModifyRoom) {
            //Modify Form
            String roomId = request.getParameter("modify_room"); //Get room id
            loadModifyForm(roomId); //Load the modification form
        } else if (isSaveRoom) {
            //Save
            String roomId = request.getParameter("save_room");
            modifyRoom(roomId);
            response.sendRedirect("/landlord"); //Redirect
        } else if (isAddRoom) {
            //Add
            String value = request.getParameter("add_room");
            if (value.equals("add")) {
                //Process
                addRoom(); //Add the new room to the landlord room list
                response.sendRedirect("/landlord"); //Redirect
            } else {
                //Form
                loadAddForm();
            }
        }
    }

    private void addRoom() throws ServletException, IOException {
        HttpSession session = request.getSession(); //Get session
        Landlord landlord = (Landlord) session.getAttribute("User"); //Get current landlord

        //Gather form values
        String cityParam = request.getParameter("city");
        String sizeParam = request.getParameter("size");
        String priceParam = request.getParameter("price");

        boolean cityValid = cityParam != null && cityParam.length() > 0;
        boolean sizeValid = sizeParam != null && sizeParam.length() > 0;
        boolean priceValid = priceParam != null && priceParam.length() > 0;

        if (cityValid && sizeValid && priceValid) {
            List<Room> rooms = getRooms(landlord); //Get the rooms from landlord

            rooms.add(new Room( //Add new room
                    Integer.parseInt(sizeParam),
                    Integer.parseInt(priceParam),
                    cityParam));
        }
    }

    /**
     * Delete the room specified.
     */
    private void deleteRoom(String parameter) {
        assert parameter != null : "Parameter is null";
        int id = Integer.parseInt(parameter); //Translate room id

        Room room = getRoom(id); //Get room by id

        HttpSession session = request.getSession(); //Get session
        Landlord landlord = (Landlord) session.getAttribute("User"); //Get the current landlord

        List<Room> rooms = getRooms(landlord); //Get rooms that belong to the landlord
        rooms.remove(room); //Remove the room from the list
    }

    private void modifyRoom(String parameter) {
        int roomId = Integer.parseInt(parameter); //Translate room id
        Room room = getRoom(roomId); //Get room

        String cityParam = request.getParameter("city");
        String sizeParam = request.getParameter("size");
        String priceParam = request.getParameter("price");

        boolean cityValid = cityParam != null && cityParam.length() > 0;
        boolean sizeValid = sizeParam != null && sizeParam.length() > 0;
        boolean priceValid = priceParam != null && priceParam.length() > 0;
        if (cityValid && sizeValid && priceValid) {
            room.setCity(cityParam);

            int size = Integer.parseInt(sizeParam);
            room.setSize(size);

            int price = Integer.parseInt(priceParam);
            room.setPrice(price);
        }
    }

    private void loadAddForm() throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println("<form method=\"post\">\n" +
                "    <table>\n" +
                "        <tr><td>City</td><td><input type=\"text\" name=\"city\" value=\"\"></td></tr>\n" +
                "        <tr><td>Size</td><td><input type=\"text\" name=\"size\" value=\"\"></td></tr>\n" +
                "        <tr><td>Price</td><td><input type=\"text\" name=\"price\" value=\"\"></td></tr>\n" +
                "        <tr><td><button type=\"submit\" name=\"add_room\" value=\"add\">Add</button></td><td><button type=\"button\" onclick=\"window.location.href='landlord'\">Back</button></td></tr>\n" +
                "    </table>\n" +
                "</form>");
    }

    private void loadModifyForm(String parameter) throws IOException {
        int roomId = Integer.parseInt(parameter); //Translate room id
        Room room = getRoom(roomId); //Get room

        PrintWriter writer = response.getWriter();
        writer.println("<form method=\"post\">\n" +
                "    <table>\n" +
                "        <tr><td>City</td><td><input type=\"text\" name=\"city\" value=\"" + room.getCity() + "\"></td></tr>\n" +
                "        <tr><td>Size</td><td><input type=\"text\" name=\"size\" value=\"" + room.getSize() + "\"></td></tr>\n" +
                "        <tr><td>Price</td><td><input type=\"text\" name=\"price\" value=\"" + room.getPrice() + "\"></td></tr>\n" +
                "        <tr><td><button type=\"submit\" name=\"save_room\" value=\"" + room.getId() + "\">Save</button></td><td><button type=\"button\" onclick=\"window.location.href='landlord'\">Back</button></td></tr>\n" +
                "    </table>\n" +
                "</form>");
    }
}