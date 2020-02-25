<%@ page import="utils.App" %>
<%@ page import="utils.Restaurant" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="utils.PartyFood" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Food Party</title>
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
        .old-price {
            text-decoration: line-through;
        }
    </style>
</head>
<%
    App app = App.getInstance();
    ArrayList<Restaurant> restaurants = app.getClosePartyRestaurants(170);
%>
<body>
<ul>
    <% for (Restaurant restaurant: restaurants) {
        ArrayList<PartyFood> partyFoods = restaurant.getPartyFoods();
        for (PartyFood partyFood: partyFoods) {
    %>
            <li>menu:
                <ul>
                    <li>
                        <img src=<%= partyFood.getImage() %> alt="logo">
                        <div><%= restaurant.getName() %></div>
                        <div><%= partyFood.getName() %></div>
                        <div><%= partyFood.getDescription() %></div>
                        <div class="old-price"><%= partyFood.getPrice() %> Toman</div>
                        <div><%= partyFood.getNewPrice() %> Toman</div>
                        <div>remaining count: <%= partyFood.getCount() %></div>
                        <div>popularity: <%= partyFood.getPopularity() %></div>
                        <form action="/addToCart" method="POST">
                            <input name="foodName" type="hidden" value="<%=partyFood.getName()%>">
                            <input name="restaurantId" type="hidden" value="<%=restaurant.getId()%>">
                            <input name="foodType" type="hidden" value="party">
                            <button type="submit">addToCart</button>
                        </form>
                    </li>
                </ul>
            </li>
    <%    }
    }
    %>
</ul>
</body>
</html>
