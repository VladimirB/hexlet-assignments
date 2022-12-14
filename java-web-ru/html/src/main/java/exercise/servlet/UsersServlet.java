package exercise.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UsersServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            showUsers(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String id = ArrayUtils.get(pathParts, 1, "");

        showUser(request, response, id);
    }

    private List<User> getUsers() throws JsonProcessingException, IOException {
        // BEGIN
        File file = new File("src/main/resources/users.json");
        return objectMapper.readValue(file, new TypeReference<>() {});
        // END
    }

    private void showUsers(HttpServletRequest request,
                           HttpServletResponse response)
            throws IOException {

        // BEGIN
        response.setContentType("text/html;charset=UTF-8");
        try (var writer = response.getWriter()) {
            var users = getUsers();

            StringBuilder sb = new StringBuilder();
            sb.append("""
                    <html lang="en" dir="ltr">
                        <head>
                            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
                            <meta charset="utf-8">
                            <title>My test title</title>
                        </head>
                        <body>
                            <table>
                                <tr>
                                    <td>ID</td>
                                    <td>First Name</td>
                                </tr>
                        
                    """);

            users.forEach(user -> {
                var row = String.format("""
                                <tr>
                                    <td>%s</td>
                                    <td>
                                        <a href="users/%s">%s</a>
                                    </td>
                                </tr>
                                """,
                        user.getId(),
                        user.getId(),
                        user.getFullName());
                sb.append(row);
            });

            sb.append("""
                    </table>
                    </body>
                    </html>
                    """);

            writer.print(sb);
        }
        // END
    }

    private void showUser(HttpServletRequest request,
                          HttpServletResponse response,
                          String id)
            throws IOException {

        // BEGIN
        List<User> users = getUsers();
        var user = users.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
        response.setContentType("text/plain;charset=UTF-8");
        if (user.isPresent()) {
            try (var writer = response.getWriter()) {
                writer.println(user.get());
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
        }
        // END
    }
}
