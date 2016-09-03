package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles authentication of the users.
 * <p>
 * Created by Derwin on 03-Sep-16.
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*IMPLEMENTATION
        * Should replace the cookie with
        * one that has 0 max age
        * */
    }
}
