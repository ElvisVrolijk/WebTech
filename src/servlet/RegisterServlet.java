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

/**
 * Created by e_voe_000 on 8/31/2016.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private Database database = Database.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userType = request.getParameter("user");

        response.setContentType("text/html");

        PrintWriter writer = response.getWriter();

        //Preconditions
        if (username.isEmpty() || password.isEmpty()) {
            writer.println("Username or password is empty.");
        }
        if (userType.isEmpty()) {
            writer.println("User type is not specified.");
        }

        User user;
        if (userType.equals("Landlord")) {
            user = new Landlord(firstName, lastName, username, password);
        } else {
            user = new Tenant(firstName, lastName, username, password);
        }
        database.addUser(user);

        writer.println("" +
                "<body>" +
                "<h1>Welcome " + firstName + "</h1>" +
                "Your username is " + username + " and you're registered as a " + userType +
                "</body>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
