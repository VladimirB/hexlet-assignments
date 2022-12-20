<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add new user</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
            crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
            <a href="/users">Все пользователи</a>
            <!-- BEGIN -->
            <form action="/users/new" method="post">
                <div class="mb-3">
                    <label for="firstName">First Name:</label>
                    <input class="form-control" type="text" name="firstName" value="${firstName}">

                    <label for="lastName">Last Name:</label>
                    <input class="form-control" type="text" name="lastName" value="${lastName}">

                    <label for="email">Email:</label>
                    <input class="form-control" type="text" name="email" value="${email}">
                </div>
                <p style="color:#FF0000">${error == null ? "" : error}</p>
                <button type="submit" name="btn btn-primary">Create</button>
            </form>
            <!-- END -->
        </div>
    </body>
</html>
