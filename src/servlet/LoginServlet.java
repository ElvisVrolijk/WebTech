package servlet;

import user.Landlord;
import user.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Handles login requests.
 * <p>
 * Created by e_voe_000 on 8/30/2016.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Whenever there is no POST information, this will redirect to the original page
        response.sendRedirect("/login.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Gather information from the POST
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //Prepare response
        response.setContentType("text/html");
        response.setStatus(200);

        ServletContext servletContext = getServletContext();
        List<User> users = (List<User>) servletContext.getAttribute("Users");

        if (users.size() == 0) {
            response.sendRedirect("/login.html");
            return;
        }

        for (User user : users) {
            String registeredUsername = user.getUsername();
            String registeredPassword = user.getPassword();
            boolean usernameMatch = registeredUsername.equals(username);
            boolean passwordMatch = registeredPassword.equals(password);

            if (usernameMatch && passwordMatch) {
                //Access Granted
                HttpSession session = request.getSession();
                session.setAttribute("User", user);
                if (user instanceof Landlord) {
                    response.sendRedirect("/landlord");
                } else {
                    response.sendRedirect("/tenant");
                }
                return;
            }
        }

        //Access Denied
        response.sendRedirect("/errorCredentials.html");
    }
}