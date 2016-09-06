package servlet;

import user.Landlord;
import user.Tenant;
import user.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Handles the register form input.
 * <p>
 * Created by e_voe_000 on 8/31/2016.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Whenever there is no POST information, this will redirect to the original page
        response.sendRedirect("/register.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Gather information from the POST
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userType = request.getParameter("user");

        //Prepare response
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        response.setStatus(200);

        ServletContext servletContext = getServletContext();
        List<User> users = (List<User>) servletContext.getAttribute("Users");

        for (User user : users) {
            String registeredUsername = user.getUsername();
            boolean usernameMatch = registeredUsername.equals(username);

            if (usernameMatch) {
                //Failed
                writer.println("Username already exist");
                return;
            }
        }

        User newUser = null;
        boolean isTenant = userType.equals("Tenant");
        boolean isLandlord = userType.equals("Landlord");

        try {
            if (isLandlord) {
                newUser = new Landlord(firstName, lastName, username, password);
            } else if (isTenant) {
                newUser = new Tenant(firstName, lastName, username, password);
            }
        } catch (IllegalArgumentException e) {
            writer.println(e.getMessage());
            return;
        }

        users.add(newUser); //Add the user to the list
        writer.println("Successfully registered as " + username);
    }
}