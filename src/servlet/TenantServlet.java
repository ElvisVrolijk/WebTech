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
 * This servlet handles request from tenant (user).
 * <p>
 * Created by Derwin on 06-Sep-16.
 */
@WebServlet("/tenant")
public class TenantServlet extends AbstractUserServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");

        boolean isTenant = user instanceof Tenant;
        boolean isLandlord = user instanceof Landlord;
        if (isTenant) {
            request.getRequestDispatcher("/WEB-INF/tenant.html")
                    .forward(request, response);
        } else if (isLandlord) {
            response.sendRedirect("/login.html");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");

        boolean isTenant = user instanceof Tenant;
        if (isTenant) {
            ServletContext servletContext = getServletContext();
            List<Room> rooms = (List<Room>) servletContext.getAttribute("Rooms");

            PrintWriter writer = response.getWriter();

            String city = request.getParameter("city");
            String sizeParam = request.getParameter("size");
            String priceParam = request.getParameter("price");

            int size = 0;
            int price = 0;
            if (sizeParam != null && priceParam != null) {
                if (!sizeParam.isEmpty() && !priceParam.isEmpty()) {
                    size = Integer.parseInt(sizeParam);
                    price = Integer.parseInt(priceParam);
                }
            }

            writer.println("<table>");
            writer.println("<tr>");
            writer.println("<th>City</th>");
            writer.println("<th>Size</th>");
            writer.println("<th>Price</th>");
            writer.println("</tr>");
            for (Room room : rooms) {
                boolean sameCity = room.getCity().equals(city);
                boolean isBigger = room.getSize() >= size;
                boolean isLessExpensive = room.getPrice() <= price;

                if (sameCity || isBigger || isLessExpensive) {
                    writer.println("<tr>");
                    writer.println("<td>" + room.getCity() + "</td>");
                    writer.println("<td>" + room.getSize() + "</td>");
                    writer.println("<td>" + room.getPrice() + "</td>");
                    writer.println("<td><button type='submit' name='Rent' value='" + room.getId() + "'>Rent</button></td>");
                    writer.println("</tr>");
                } else {
                    writer.println("<tr><td>No rooms match criteria</td></tr>");
                    break;
                }
                writer.println("<button type='button' onclick='window.location.href=\"tenant\"'></button>");
            }
            writer.println("</table>");
        }
    }
}
