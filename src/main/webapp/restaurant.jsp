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

    <!-- IN CASE YOU WANT SOME BONUS : -->
    <!-- <li>estimated delivery time: 10 min 2 sec </li> -->

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