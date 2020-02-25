<%@ page import="utils.App" %>
<%@ page import="utils.Order" %>
<%@ page import="utils.Food" %>
<%@ page import="java.util.Map" %>
<%@ page import="utils.PartyFood" %>
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

    <%
        for (Map.Entry<PartyFood, Integer> entry : order.getPartyFoods().entrySet()) {
    %>
    <li>( *** party *** ) <%=entry.getKey().getName()%>:‌<%=entry.getValue()%></li>
    <%
        }
    %>
</ul>
<div>
    status : <%=order.getStatus()%>
    <%
        if (order.getStatus().equals("delivering")) {
            String timeRemaining;
            int minutes = 0, seconds;
            long currTime = System.nanoTime();
            long timeDiff = (long) order.getRemainingTime() - ((currTime - order.getDeliveryBeginTime()) / 1000000000);
            while (timeDiff >= 60) {
                timeDiff -= 60;
                minutes++;
            }
            seconds = (int) timeDiff;
            timeRemaining = "remained time : " + minutes + " min " + seconds + " sec";
            out.println("<div>" + timeRemaining + "</div>");
        }
    %>
</div>
</body>
</html>
