package servlet;

import user.Landlord;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handles request for the landlord page.
 * <p>
 * Created by Derwin on 06-Sep-16.
 */
@WebServlet("/landlord")
public class LandlordServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Load the session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");

        if (user != null) {
            //Access Granted
            boolean isLandlord = user instanceof Landlord;
            if (isLandlord) {
                request.getRequestDispatcher("/WEB-INF/landlord.html")
                        .forward(request, response);
            } else {
                response.sendRedirect("/login.html");
            }
        } else {
            //Access Denied
            response.sendRedirect("/login.html");
        }
    }
}
