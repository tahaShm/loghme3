<%@ page import="utils.App" %>
<%@ page import="utils.Order" %>
<%@ page import="utils.Food" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    App app = App.getInstance();
    Order order = app.getCustomer().getOrderById(Integer.valueOf(request.getParameter("id")));
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
<div><%=order.getRestaurant().getName()%></div>
<ul>
    <%
        for (Map.Entry<Food, Integer> entry : order.getFoods().entrySet()) {
    %>
    <li><%=entry.getKey().getName()%>:‌<%=entry.getValue()%></li>
    <%
        }
    %>
</ul>
<div>
    status : <%=order.getStatus()%>
</div>
</body>
</html>
