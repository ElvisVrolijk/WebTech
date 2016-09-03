package servlet;

import admin.Database;
import exception.UserNotFoundException;
import user.Landlord;
import user.Tenant;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles login requests.
 * <p>
 * Created by e_voe_000 on 8/30/2016.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////
    private Database database = Database.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Gather information from the POST
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //Prepare response
        response.setContentType("text/html");
        response.setStatus(200);

        try {
            User user = database.getUserByUsername(username);
            String registeredPassword = user.getPassword();
            if (registeredPassword.equals(password)) {
                //Password is correct
                if (user instanceof Tenant) { //Check what type of user logged in
                    request.getRequestDispatcher("/WEB-INF/tenant.html").forward(request, response);
                } else if (user instanceof Landlord) {
                    request.getRequestDispatcher("/WEB-INF/addroom.html");
                }
            } else {
                //Password is incorrect
                // TODO: 03-Sep-16 Ask whether there is a possible way to pass on an argument to error.html
                response.sendRedirect("/error.html");
            }
        } catch (UserNotFoundException e) {
            response.sendRedirect("/error.html");
        }

    }
}
