package utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.exceptions.OrderNotFound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Customer {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private int credit;
    ArrayList<Order> orders = new ArrayList<>();
    Order currentOrder = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public ArrayList<Order> getOrders() { return orders; }

    public void setOrders(ArrayList<Order> orders) { this.orders = orders; }

    public Order getCurrentOrder() { return currentOrder; }

    public void setCurrentOrder(Order order) { this.currentOrder = order; }

    public void addOrder(Order order) { orders.add(order); }

    public void addFoodToCurrentOrder(Food food) {
        currentOrder.addFood(food);
    }

    public void emptyCurrentOrder() {currentOrder = null;}

    public String getCartJson() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapperObj = new ObjectMapper();
        String foodCartJson = mapperObj.writeValueAsString(currentOrder);
        return foodCartJson;
    }

    public void addCredit(int toAddCredit) {
        credit += toAddCredit;
    }

    public Order getOrderById(int id) throws OrderNotFound {
        for (Order order: orders) {
            if (order.getId() == id)
                return order;
        }
        throw new OrderNotFound();
    }

    public int cartOverallPrice() {
        return currentOrder.overallPrice();
    }
}
