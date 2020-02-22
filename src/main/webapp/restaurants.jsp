<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restaurants</title>
    <style>
        table {
            text-align: center;
            margin: auto;
        }
        th, td {
            padding: 5px;
            text-align: center;
        }
        .logo{
            width: 100px;
            height: 100px;
        }
    </style>
</head>
<%@ page import="utils.App" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="utils.Restaurant" %><%
    App app = App.getInstance();
    ArrayList<Restaurant> restaurants = app.getCloseRestaurants(170);
%>
<body>
<table>
    <tr>
        <th>id</th>
        <th>logo</th>
        <th>name</th>
        <th>location</th>
    </tr>
    <% for (Restaurant restaurant: restaurants) { %>
    <tr>
        <td><%= restaurant.getId() %></td>
        <td><img class="logo" src=<%= restaurant.getLogo() %> alt="logo"></td>
        <td><%= restaurant.getName() %></td>
        <td>(<%= restaurant.getLocation().getX() %>, <%= restaurant.getLocation().getY() %>)</td>
    </tr>
    <% } %>
</table>
</body>
</html>