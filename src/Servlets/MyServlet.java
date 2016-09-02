package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by e_voe_000 on 8/30/2016.
 */
@WebServlet("/main")
public class MyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        response.setContentType("text/html");

        PrintWriter writer = response.getWriter();
        writer.println("<html><head></head><body>");
        writer.println("<h1>Welcome " + name + "</h1>");
        writer.println("</body></html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
