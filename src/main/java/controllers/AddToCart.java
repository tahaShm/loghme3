package controllers;

import utils.App;
import utils.Food;
import utils.PartyFood;
import utils.Restaurant;
import utils.exceptions.ExtraFoodPartyExp;
import utils.exceptions.FoodFromOtherRestaurantInCartExp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@WebServlet("/addToCart")
public class AddToCart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        App app = App.getInstance();
        response.setContentType("text/html;charset=UTF-8");

        String foodName = request.getParameter("foodName");
        byte[] bytes = foodName.getBytes(StandardCharsets.ISO_8859_1);
        foodName = new String(bytes, StandardCharsets.UTF_8);
        String restaurantId = request.getParameter("restaurantId");
        String foodType = request.getParameter("foodType");

        try {
            Restaurant restaurant = app.getRestaurantById(restaurantId);
            PartyFood partyFoodTest = app.getPartyFoodByName(foodName, restaurant);
            if (foodType.equals("party") && partyFoodTest != null && partyFoodTest.getCount() > 0){
                PartyFood partyFood = partyFoodTest;
                app.addToCart(partyFood, restaurant);
//                partyFood.reduceCount();
            }
            else if (foodType.equals("party") && partyFoodTest != null && partyFoodTest.getCount() <= 0){
                response.setStatus(403);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/forbidden.jsp");
                requestDispatcher.forward(request, response);
            }
            else if (foodType.equals("normal")) {
                Food food = app.getFoodByName(foodName, restaurant);
                app.addToCart(food, restaurant);
            }
        }
        catch (Exception e) {
            if (e instanceof FoodFromOtherRestaurantInCartExp || e instanceof ExtraFoodPartyExp) {
                response.setStatus(403);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/forbidden.jsp");
                requestDispatcher.forward(request, response);
            }
        }
        response.setStatus(200);
        if (foodType.equals("normal")) {
            request.setAttribute("id", app.getCustomer().getCurrentOrder().getRestaurant().getId());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/restaurant.jsp");
            requestDispatcher.forward(request, response);
        }
        else if (foodType.equals("party")){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/party.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}