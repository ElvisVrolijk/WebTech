package servlet;

import admin.Room;
import user.Landlord;
import user.Tenant;
import user.User;

import javax.servlet.ServletContext;
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

        //Get session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");

        boolean isLandlord = user instanceof Landlord;
        boolean isTenant = user instanceof Tenant;
        if (isLandlord) {
            ServletContext servletContext = getServletContext();
            List<Room> rooms = (List<Room>) servletContext.getAttribute("Rooms");
            PrintWriter writer = response.getWriter();

            if (rooms.size() == 0) {
                //Incase there is no room
                writer.println("No rooms to show");
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
        } else if (isTenant) {
            response.sendRedirect("/tenant");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response); //Checks whether the user is logged in

        //Get session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");

        //Check which user is online
        boolean isLandlord = user instanceof Landlord;
        boolean isTenant = user instanceof Tenant;
        if (isLandlord) { //Landlord
            //Get the input from the user
            String deleteRoom = request.getParameter("delete_room");
            String modifyRoom = request.getParameter("modify_room");
            String saveRoom = request.getParameter("save_room");
            String addRoom = request.getParameter("add_room");

            if (deleteRoom != null) {
                //Remove
                int id = Integer.parseInt(deleteRoom);
                deleteRoom(id);
                response.sendRedirect("/landlord");
            } else if (modifyRoom != null) {
                //Modify
                int id = Integer.parseInt(modifyRoom);

                PrintWriter writer = response.getWriter();
                new ModifyPage(writer, id);
            } else if (saveRoom != null) {
                //Save
                int id = Integer.parseInt(saveRoom);
                new SavingPage(id, request);
                response.sendRedirect("/landlord");
            } else if (addRoom.equals("0")) {
                //Add room form
                PrintWriter writer = response.getWriter();
                new AddPage(writer);
            } else if (addRoom.equals("1")) {
                //Add room
                String city = request.getParameter("city");
                String size = request.getParameter("size");
                String price = request.getParameter("price");
                if (city != null && size != null && price != null) {
                    if (city.length() > 0 && size.length() > 0 && price.length() > 0) {
                        addRoom(city, Integer.parseInt(size), Integer.parseInt(price));
                        response.sendRedirect("/landlord");

                    }
                }
            }
        } else if (isTenant) { //Tenant
            response.sendRedirect("/tenant");
        }
    }

    private void deleteRoom(int id) {
        ServletContext servletContext = getServletContext();
        List<Room> rooms = (List<Room>) servletContext.getAttribute("Rooms");

        Room roomToDelete = null;
        for (Room room : rooms) {
            if (room.getId() == id) {
                roomToDelete = room;
            }
        }
        rooms.remove(roomToDelete);
    }

    private void addRoom(String city, int size, int price) {
        List<Room> rooms = getRooms();
        rooms.add(new Room(size, price, city));
    }

    private class ModifyPage {
        ModifyPage(PrintWriter writer, int id) {
            Room room = getRoom(id);

            writer.println("<form method='post'>");
            writer.println("<table>");
            writer.println("<tr><td>City</td><td><input type='text' name='city' value='" + room.getCity() + "' ></td></tr>");
            writer.println("<tr><td>Size</td><td><input type='text' name='size' value='" + room.getSize() + "' ></td></tr>");
            writer.println("<tr><td>Price</td><td><input type='text' name='price' value='" + room.getPrice() + "' ></td></tr>");
            writer.println("<tr><td><button type='submit' name='save_room' value='" + room.getId() + "'>Save</button></td>" +
                    "<td><button type='button' onclick='window.location.href=\"landlord\"'>Cancel</button></td></tr>");
            writer.println("</table>");
            writer.println("</form>");
        }
    }

    private class SavingPage {
        SavingPage(int id, HttpServletRequest request) {
            Room room = getRoom(id);

            String city = request.getParameter("city");
            String size = request.getParameter("size");
            String price = request.getParameter("price");

            if (city != null && size != null && price != null) { //Check if parameters are valid
                if (city.length() != 0 && size.length() > 0 && price.length() > 0) {
                    room.setCity(city);
                    room.setSize(Integer.parseInt(size)); //Parse string to integer
                    room.setPrice(Integer.parseInt(price)); //Parse string to integer
                }
            }
        }
    }

    private class AddPage {

        AddPage(PrintWriter writer) {
            writer.println("<form method='post'>");
            writer.println("<table>");
            writer.println("<tr><td>City</td><td><input type='text' name='city' placeholder='City'</td></tr>");
            writer.println("<tr><td>Size</td><td><input type='text' name='size' placeholder='Size in Square Meters'</td></tr>");
            writer.println("<tr><td>Price</td><td><input type='text' name='price' placeholder='Price'</td></tr>");
            writer.println("<tr><td><button type='submit' name='add_room' value='1'>Add</button></td>" +
                    "<td><button type='button' onclick='window.location.href=\"landlord\"'>Cancel</button></td></tr>");
            writer.println("</table>");
            writer.println("</form>");
        }
    }

}
