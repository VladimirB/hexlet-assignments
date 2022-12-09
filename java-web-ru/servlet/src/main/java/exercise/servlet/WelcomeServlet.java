package exercise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// BEGIN
public class WelcomeServlet extends HttpServlet {

    public static final String NAME = "WelcomeServlet";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        try (var writer = resp.getWriter()) {
            writer.println("Hello, Hexlet!");
        }
    }
}
// END
