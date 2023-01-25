package exercise.servlet;

import exercise.Users;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static exercise.App.getUsers;

public class SessionServlet extends HttpServlet {

    private Users users = getUsers();

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        if (request.getRequestURI().equals("/login")) {
            showLoginPage(request, response);
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        switch (request.getRequestURI()) {
            case "/login" -> login(request, response);
            case "/logout" -> logout(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showLoginPage(HttpServletRequest request,
                               HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
        requestDispatcher.forward(request, response);
    }

    private void login(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        // BEGIN
        HttpSession session = request.getSession();

        var email = request.getParameter("email");
        var password = request.getParameter("password");

        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            session.setAttribute("flash", "Неверные логин или пароль");
            response.setStatus(422);
            response.sendRedirect("/login");
            return;
        }

        var user = getUsers().findByEmail(email);
        if (user != null && user.get("email").equals(email)) {

            String page;
            if (user.get("password").equals(password)) {
                page = "/welcome.jsp";
                session.setAttribute("userId", user.get("id"));
                session.setAttribute("flash", "Вы успешно вошли");
            } else {
                page = "/login.jsp";
                session.setAttribute("flash", "Неверные логин или пароль");
                response.setStatus(422);
            }

            RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
            request.setAttribute("user", user);
            requestDispatcher.forward(request, response);
        }
        // END
    }

    private void logout(HttpServletRequest request,
                        HttpServletResponse response)
            throws IOException {

        // BEGIN
        var session = request.getSession();
        session.removeAttribute("userId");
        session.setAttribute("flash", "Вы успешно вышли");
        response.sendRedirect("/");
        // END
    }
}
