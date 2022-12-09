package exercise.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// BEGIN
//@WebServlet(
//        name = "WelcomeServlet",
//        urlPatterns = {"/hello"}
//)
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter w = resp.getWriter()) {
            w.println("Hello, Hexlet!");
        }
    }
}
// END
