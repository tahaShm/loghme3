package utils;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private HashMap<Food, Integer> foods = new HashMap<>();
    private int id;
    private float remainingTime;
    private String status;
    Restaurant restaurant;

    public Order(int id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
    }

    public void setId(int id) {this.id = id;}

    public void setRemainingTime(float remainingTime) {this.remainingTime = remainingTime;}

    public void setStatus(String status) {this.status = status;}

    public HashMap<Food, Integer> getFoods() {return foods;}

    public int getId() {return id;}

    public float getRemainingTime() {return remainingTime;}

    public String getStatus() {return status;}

    public Restaurant getRestaurant() {return restaurant;}

    public void addFood(Food food) {
        if (foods.containsKey(food))
            foods.put(food, foods.get(food) + 1);
        else
            foods.put(food, 1);
    }

    public int overallPrice() {
        int price = 0;
        for (Map.Entry<Food, Integer> entry: foods.entrySet()) {
            price += entry.getKey().getPrice() * entry.getValue();
        }
        return price;
    }
}
