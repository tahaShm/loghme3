<%@ page import="utils.App" %>
<%@ page import="utils.Restaurant" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="utils.Food" %>
<%@ page import="java.util.Map" %>
<%@ page import="utils.PartyFood" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    App app = App.getInstance();
    Restaurant restaurant = app.getCustomer().getCurrentOrder().getRestaurant();
    HashMap<Food, Integer> foods = app.getCustomer().getCurrentOrder().getFoods();
    HashMap<PartyFood, Integer> partyFoods = app.getCustomer().getCurrentOrder().getPartyFoods();
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

    <%
        for (Map.Entry<PartyFood, Integer> entry : partyFoods.entrySet()) {
    %>
    <li>( *** party *** ) <%=entry.getKey().getName()%>:‌<%=entry.getValue()%></li>
    <%
        }
    %>
</ul>
<form action="/finalize" method="POST">
    <button type="submit">finalize</button>
</form>
</body>
</html>
