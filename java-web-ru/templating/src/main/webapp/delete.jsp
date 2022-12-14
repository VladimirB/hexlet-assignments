<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en" dir="ltr">
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta charset="utf-8">
        <title>Delete User ${user.get("id")}</title>
    </head>
    <body>
        <p>Are you really want to delete ${user.get("firstName")} ${user.get("lastName")}?</p>
        <form action="/users/delete?id=${user.get("id")}" method="post">
            <button type="submit" class="btn btn-danger">Delete</button>
        </form>
    </body>
</html>

