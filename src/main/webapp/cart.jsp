<%@ page import="utils.App" %>
<%@ page import="utils.Restaurant" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="utils.Food" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    App app = App.getInstance();
    Restaurant restaurant = app.getCustomer().getCurrentOrder().getRestaurant();
    HashMap<Food, Integer> foods = app.getCustomer().getCurrentOrder().getFoods();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
    <style>
        li, div, form {
            padding: 5px
        }
    </style>
</head>
<body>
<div><%=restaurant.getName()%></div>
<ul>
    <%
        for (Map.Entry<Food, Integer> entry : foods.entrySet()) {
    %>
    <li><%=entry.getKey().getName()%>:‌<%=entry.getValue()%></li>
    <%
        }
    %>
</ul>
<form action="/finalize" method="POST">
    <button type="submit">finalize</button>
</form>
</body>
</html>
