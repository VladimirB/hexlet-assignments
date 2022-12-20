package exercise.servlet;

import exercise.Data;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static exercise.Data.getUsers;

public class UsersServlet extends HttpServlet {

    private final List<Map<String, String>> users = getUsers();

    private String getId(HttpServletRequest request) {
        return request.getParameter("id");
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return "list";
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 1, "");
    }

    private Map<String, String> getUserById(String id) {
        return users
            .stream()
            .filter(u -> u.get("id").equals(id))
            .findAny()
            .orElse(null);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = getAction(request);
        switch (action) {
            case "list" -> showUsers(request, response);
            case "new" -> newUser(request, response);
            case "edit" -> editUser(request, response);
            case "show" -> showUser(request, response);
            case "delete" -> deleteUser(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = getAction(request);
        switch (action) {
            case "new" -> createUser(request, response);
            case "edit" -> updateUser(request, response);
            case "delete" -> destroyUser(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showUsers(HttpServletRequest request,
                           HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("users", users);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/users.jsp");
        requestDispatcher.forward(request, response);
    }


    private void showUser(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        String id = getId(request);
        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("user", user);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/show.jsp");
        requestDispatcher.forward(request, response);
    }

    private void newUser(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/new.jsp");
        dispatcher.forward(request, response);
    }

    private void createUser(HttpServletRequest request,
                            HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("error", "");

        var email = request.getParameter("email");
        var firstName = request.getParameter("firstName");
        var lastName = request.getParameter("lastName");
        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
            response.setStatus(422);

            request.setAttribute("error", "422 (Unprocessable Entity)");
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/new.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Map<String, String> user = new HashMap<>();
        user.put("id", Data.getNextId());
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("email", email);
        users.add(user);

        showUsers(request, response);
    }

    private void editUser(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        String id = getId(request);
        Map<String, String> user = getUserById(id);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void updateUser(HttpServletRequest request,
                            HttpServletResponse response) throws IOException, ServletException {
        String id = getId(request);
        Map<String, String> user = getUserById(id);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        var email = request.getParameter("email");
        var firstName = request.getParameter("firstName");
        var lastName = request.getParameter("lastName");
        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
            Map<String, String> editedUser = new HashMap<>();
            editedUser.put("id", id);
            editedUser.put("firstName", firstName);
            editedUser.put("lastName", lastName);
            editedUser.put("email", email);

            request.setAttribute("user", editedUser);
            request.setAttribute("error", "422 (Unprocessable Entity)");
            response.setStatus(422);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/edit.jsp");
            dispatcher.forward(request, response);
            return;
        }

        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("email", email);

        request.setAttribute("error", "");
        request.setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/show.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        request.setAttribute("user", user);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete.jsp");
        requestDispatcher.forward(request, response);

    }

    private void destroyUser(HttpServletRequest request,
                         HttpServletResponse response)
                 throws IOException, ServletException {

        String id = getId(request);

        Map<String, String> user = getUserById(id);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        users.remove(user);
        response.sendRedirect("/users");
    }
}
