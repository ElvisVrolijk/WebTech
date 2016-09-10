package servlet;

import admin.Room;
import user.Tenant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * This servlet handles request from tenant (user).
 * <p>
 * Created by Derwin on 06-Sep-16.
 */
@WebServlet("/tenant")
public class TenantServlet extends AbstractUserServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);

        if (!response.isCommitted()) {
            request.getRequestDispatcher("/WEB-INF/tenant.html")
                    .forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        boolean rentRoom = request.getParameter("rent_room") != null;
        PrintWriter writer = response.getWriter();

        if (rentRoom) {
            HttpSession session = request.getSession(); //Get session
            Tenant tenant = (Tenant) session.getAttribute("User"); //Get user that rented the room

            if (tenant.hasRented()) {
                writer.println("You have already rented a room <button type='button' onclick='window.location.href=\"tenant\"'>Ok</button>");
                return;
            }

            String roomId = request.getParameter("rent_room"); //Get room id
            Room room = getRoom(Integer.parseInt(roomId)); //Get the room reference

            tenant.rentRoom(room); //Rent the room

            writer.println("You have rented a room. <button type='button' onclick='window.location.href=\"tenant\"'>Ok</button>");
            return;
        }
        List<Room> rooms = getRooms(); //Get list of rooms from all landlords

        //Get values
        String cityParam = request.getParameter("city");
        String sizeParam = request.getParameter("size");
        String priceParam = request.getParameter("price");

        boolean sizeValid = sizeParam == null || !sizeParam.isEmpty();
        boolean priceValid = priceParam == null || !priceParam.isEmpty();

        if (!sizeValid) {
            //In case the user didn't type any value
            sizeParam = "0";
        }

        if (!priceValid) {
            //In case the user didn't type any value
            priceParam = String.valueOf(Integer.MAX_VALUE);
        }


        writer.println("<table>");
        writer.println("<form method='post'>");
        writer.println("<tr>");
        writer.println("<th>City</th>");
        writer.println("<th>Size</th>");
        writer.println("<th>Price</th>");
        writer.println("</tr>");

        for (Room room : rooms) {
            boolean sameCity = room.getCity().equals(cityParam) || cityParam.equals("");
            boolean isBigger = room.getSize() >= Integer.parseInt(sizeParam);
            boolean isLessExpensive = room.getPrice() <= Integer.parseInt(priceParam);

            if (sameCity && isBigger && isLessExpensive) {
                if (room.isRented()) {
                    continue; //Skip this iteration

                }

                writer.println("<tr>");
                writer.println("<td>" + room.getCity() + "</td>");
                writer.println("<td>" + room.getSize() + "</td>");
                writer.println("<td>" + room.getPrice() + "</td>");
                writer.println("<td><button type='submit' name='rent_room' value='" + room.getId() + "'>Rent</button></td>");
                writer.println("</tr>");
            } else {
                writer.println("<tr><td>No rooms match criteria</td></tr>");
                break;
            }
        }
        writer.println("<tr><td><button type='button' onclick='window.location.href=\"tenant\"'>Back</button></td></tr>");
        writer.println("</form>");
        writer.println("</table>");
    }
}
