<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en" dir="ltr">
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <meta charset="utf-8">
        <title>User ${user.get("id")}</title>

    </head>
    <body>
        <table style="width:50%">
            <tr>
                <td>ID</td>
                <td>First Name</td>
                <td>Last Name</td>
                <td>Email</td>
            </tr>
            <tr>
                <td>${user.get("id")}</td>
                <td>${user.get("firstName")}</td>
                <td>${user.get("lastName")}</td>
                <td>${user.get("email")}</td>
                <td>
                    <a href="/users/delete?id=${user.get("id")}">Delete</a>
                </td>
            </tr>
        </table>
    </body>
</html>
