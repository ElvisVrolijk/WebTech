package servlet;

import admin.Database;
import user.Landlord;
import user.Tenant;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;

/**
 * Handles the register form input.
 * <p>
 * Created by e_voe_000 on 8/31/2016.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////
    private Database database = Database.getInstance();

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

        try {
            //User class handles errors (e.g. Empty username or password)
            if (userType.equals("Tenant")) {
                Tenant tenant = new Tenant(firstName, lastName, username, password);
                database.addUser(tenant);
            } else if (userType.equals("Landlord")) {
                Landlord landlord = new Landlord(firstName, lastName, username, password);
                database.addUser(landlord);
            } else {
                throw new RuntimeException("User type is not specified");
            }
            writer.println("Successfully registered<br> Your username is " + username + " and you're as a " + userType);
        } catch (Exception e) {
            writer.println(e.getMessage());
        }
    }
}