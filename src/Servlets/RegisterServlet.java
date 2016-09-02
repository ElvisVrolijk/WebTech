package Servlets;

import Users.Landlord;

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
@WebServlet("/Servlets.RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");

        PrintWriter writer = response.getWriter();

        if (request.getParameter("user").equals("landlord")) {

            writer.println("<html><head></head><body>");
            writer.println("<h1>Welcome " + username + "</h1>");
            writer.println("You are now registered as a landlord");
            writer.println("</body></html");
        } else {

            writer.println("<html><head></head><body>");
            writer.println("<h1>Welcome " + username + "</h1>");
            writer.println("You are now registered as a tenant");
            writer.println("</body></html");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
