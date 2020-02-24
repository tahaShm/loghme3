<%@ page import="utils.App" %>
<%@ page import="utils.Restaurant" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="utils.Food" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String id = (String) request.getAttribute("id");
    App app = App.getInstance();
    Restaurant restaurant = app.getRestaurantById(id);
    ArrayList<Food> menu = restaurant.getMenu();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restaurant</title>
    <style>
        img {
            width: 50px;
            height: 50px;
        }
        li {
            display: flex;
            flex-direction: row;
            padding: 0 0 5px;
        }
        div, form {
            padding: 0 5px
        }
    </style>
</head>
<body>
<ul>
    <li>id: 1</li>
    <li>name: <%=restaurant.getName()%></li>
    <li>location: (<%=restaurant.getLocation().getX()%>, <%=restaurant.getLocation().getY()%>)</li>
    <li>logo: <img src=<%=restaurant.getLogo()%> alt="logo"></li>

    <%
        int second = 0, minute = 1; //estimated time to find courier = 60 seconds
        double time;
        double restaurantDistance = Math.sqrt(Math.pow(restaurant.getLocation().getX(), 2) + Math.pow(restaurant.getLocation().getY(), 2));
        restaurantDistance += restaurantDistance / 2;
        time = restaurantDistance / 5;
        while (time >= 60) {
            time -= 60;
            minute++;
        }
        second = (int) time;
        out.println("<li>estimated delivery time: " + minute + " min " + second + " sec </li>");
    %>

    <li>menu:
        <ul>
            <%
                for (Food food: menu) {
            %>
            <li>
                <img src=<%=food.getImage()%> alt="logo">
                <div><%=food.getName()%></div>
                <div><%=food.getPrice()%> Toman</div>
                <form action="/addToCart" method="POST">
                    <input name="foodName" type="hidden" value="<%=food.getName()%>">
                    <input name="restaurantId" type="hidden" value="<%=restaurant.getId()%>">
                    <button type="submit">addToCart</button>
                </form>
            </li>
            <%
                }
            %>
        </ul>
    </li>
</ul>
</body>
</html>