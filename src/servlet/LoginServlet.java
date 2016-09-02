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
import java.io.WriteAbortedException;

/**
 * Created by e_voe_000 on 8/30/2016.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////
    private Database database = Database.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        response.setStatus(200);

        if (username.isEmpty() || password.isEmpty()) {
            writer.println("Username and password can not be empty.");
        }

        User user = database.getUserByUsername(username);
        String registeredPassword = user.getPassword();

        if (registeredPassword.equals(password)) { //Check whether password is correct
            //Check what type of user logged in
            if (user instanceof Tenant) {
                response.sendRedirect("/tenant.html");
            } else if (user instanceof Landlord) {
                response.sendRedirect("/landlord.html");
            }
        } else {
            writer.println("" +
                    "<html>" +
                    "<head>" +
                    "<meta http-equiv=\"refresh\"\n content=\"0; url=http://www.mydomain.com/new-page.html\">" +
                    "</head>" +
                    "<body>" +
                    "Wrong username or password." +
                    "</body>" +
                    "</html>");
        }
    }
}
