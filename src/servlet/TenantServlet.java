package servlet;

import user.Tenant;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This servlet handles request from tenant (user).
 * <p>
 * Created by Derwin on 06-Sep-16.
 */
@WebServlet("/tenant")
public class TenantServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Load the session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");

        if (user != null) {
            //Access Granted
            boolean isTenant = user instanceof Tenant;
            if (isTenant) {
                request.getRequestDispatcher("/WEB-INF/tenant.html")
                        .forward(request, response);
            } else {
                response.sendRedirect("/login.html");
            }
        }

        //Access Denied
        response.sendRedirect("/login.html");
    }
}
