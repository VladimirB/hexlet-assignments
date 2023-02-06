package exercise.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import java.util.*;

import exercise.Article;
import org.apache.commons.lang3.ArrayUtils;

import exercise.TemplateEngineUtil;
import org.apache.commons.lang3.math.NumberUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class ArticlesServlet extends HttpServlet {

    private static final int MAX_ROWS = 10;

    private static final int LIMIT_QUERY_POSITION = 1;
    private static final int OFFSET_QUERY_POSITION = 2;

    private String getId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return null;
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 1, null);
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return "list";
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 2, getId(request));
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "list":
                showArticles(request, response);
                break;
            default:
                showArticle(request, response);
                break;
        }
    }

    private void showArticles(HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException, ServletException {

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("dbConnection");
        // BEGIN
        int page = NumberUtils.toInt(request.getParameter("page"), 1);

        String query = "SELECT id, title, body FROM articles ORDER BY id LIMIT ? OFFSET ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(LIMIT_QUERY_POSITION, MAX_ROWS);
            preparedStatement.setInt(OFFSET_QUERY_POSITION, MAX_ROWS * (page - 1));

            List<Article> articles = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Article article = new Article(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("body")
                );
                articles.add(article);
            }

            request.setAttribute("articles", articles);
            request.setAttribute("page", page);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        // END
        TemplateEngineUtil.render("articles/index.html", request, response);
    }

    private void showArticle(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException, ServletException {

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("dbConnection");

        // BEGIN
        String pathInfo = request.getPathInfo();
        String[] infos = pathInfo.split("/");

        int articleId = NumberUtils.toInt(infos[infos.length - 1]);

        String sqlQuery = "SELECT id, title, body FROM articles WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, articleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            var hasRow = resultSet.first();
            if (!hasRow) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Article article = new Article(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("body")
            );
            request.setAttribute("article", article);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
        // END

        TemplateEngineUtil.render("articles/show.html", request, response);
    }
}
