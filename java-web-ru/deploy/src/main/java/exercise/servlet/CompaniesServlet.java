package exercise.servlet;

import exercise.Data;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CompaniesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        // BEGIN
        var query = request.getParameter("search");

        List<String> companies = Data.getCompanies();
        boolean hasQuery = StringUtils.isNotEmpty(query);
        if (hasQuery) {
            companies = companies.stream()
                    .filter(it -> it.contains(query))
                    .toList();
        }

        try(PrintWriter writer = response.getWriter()) {
            if (hasQuery && companies.isEmpty()) {
                writer.println("Companies not found");
            } else {
                writer.println(String.join("\n", companies));
            }
        }
        // END
    }
}
