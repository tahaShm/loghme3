package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import scheduledExecutors.CouriersScheduler;
import utils.exceptions.*;

import java.io.IOException;
import java.util.*;

public class App
{
    private ArrayList<Restaurant> restaurants;
    private Customer customer;
    private static App singleApp = null;
    private int lastOrderId = 0;

    private App() {
        restaurants = new ArrayList<>();
        customer = new Customer();
    }

    public static App getInstance() {
        if (singleApp == null)
            singleApp = new App();

        return singleApp;
    }

    public void setRestaurants(ArrayList<Restaurant> inRestaurants) { restaurants.addAll(inRestaurants); }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getIndexOfRestaurant(String jsonData, int nameOrRestaurantName) throws IOException {
        int index = -1;
        ObjectMapper nameMapper = new ObjectMapper();
        Names newName = nameMapper.readValue(jsonData, Names.class);
        String restaurantName = "";
        if (nameOrRestaurantName == 1)
            restaurantName = newName.getRestaurantName();
        else
            restaurantName = newName.getName();
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurantName.equals(restaurants.get(i).getName())) {
                index = i;
                break;
            }
        }
        return index;
    }

    public HashMap<String, Float> sortByValue(HashMap<String, Float> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Float> > list =
                new LinkedList<Map.Entry<String, Float> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Float> >() {
            public int compare(Map.Entry<String, Float> o1,
                               Map.Entry<String, Float> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Float> temp = new LinkedHashMap<String, Float>();
        for (Map.Entry<String, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private Map<String, Float> getBestRestaurants(int numOfBests) {
        HashMap<String, Float> allRestaurants = new HashMap<String, Float>();
        Map<String, Float> sortedRestaurants;

        for (Restaurant restaurant : restaurants) {
            String currentRestaurantName = restaurant.getName();
            float currentPopularity = restaurant.sendPopularity();
            allRestaurants.put(currentRestaurantName, currentPopularity);
        }

        sortedRestaurants = sortByValue(allRestaurants);
        allRestaurants.clear();
        int counter = 0;
        for (Map.Entry<String, Float> entry : sortedRestaurants.entrySet()) {
            if (counter >= numOfBests)
                break;
            allRestaurants.put(entry.getKey(), entry.getValue());
            counter++;
        }

        return allRestaurants;
    }

    public void addRestaurant(String jsonData) throws IOException, RestaurantAlreadyExistsExp {
        ObjectMapper mapper = new ObjectMapper();
        Restaurant newRestaurant = mapper.readValue(jsonData, Restaurant.class);
        for (Restaurant restaurant: restaurants) {
            if (restaurant.getName().equals(newRestaurant.getName()))
                throw new RestaurantAlreadyExistsExp();
        }
        restaurants.add(newRestaurant);
    }

    public void addFood(String jsonData) throws RestaurantNotFoundExp, IOException, FoodAlreadyExistsExp {
        ObjectMapper mapper = new ObjectMapper();
        Food newFood = mapper.readValue(jsonData, Food.class);
        int index = getIndexOfRestaurant(jsonData, 1);
        if (index >= 0)
            restaurants.get(index).addFood(newFood);
        else
            throw new RestaurantNotFoundExp();
    }

    public String getRestaurantsInfo() {
        String response = "";
        for (Restaurant restaurant : restaurants) {
            response += restaurant.getName() + '\n';
        }
        return response;
    }

    public String getRestaurant(String jsonData) throws RestaurantNotFoundExp, IOException {
        int index = getIndexOfRestaurant(jsonData, 0);
        if (index >= 0)
            return restaurants.get(index).sendJsonInfo();
        else
            throw new RestaurantNotFoundExp();
    }

    public String getFood(String jsonData) throws RestaurantNotFoundExp, IOException, FoodNotFoundExp{
        int index = getIndexOfRestaurant(jsonData, 1);

        ObjectMapper nameMapper = new ObjectMapper();
        Names newName = nameMapper.readValue(jsonData, Names.class);
        String foodName = newName.getFoodName();

        if (index >= 0)
            return restaurants.get(index).sendJsonFoodInfo(foodName);
        else
            throw new RestaurantNotFoundExp();
    }

//    public void addToCart(String jsonData) throws RestaurantNotFoundExp, FoodFromOtherRestaurantInCartExp, FoodNotFoundExp, IOException{
//        boolean allowToAdd = false;
//        ObjectMapper nameMapper = new ObjectMapper();
//        Names newName = nameMapper.readValue(jsonData, Names.class);
//        String restaurantName = newName.getRestaurantName();
//
//        if (!customer.isRestaurantSet())
//            allowToAdd = true;
//        else {
//            String currentRestaurantName = customer.getRestaurantName();
//            if (currentRestaurantName.equals(restaurantName))
//                allowToAdd = true;
//        }
//
//        if (allowToAdd) {
//            int index = getIndexOfRestaurant(jsonData, 1);
//
//            String foodName = newName.getFoodName();
//
//            if (index >= 0){
//                if (restaurants.get(index).isFoodValid(foodName)) {
//                    customer.addFoodToCart(foodName, restaurantName);
//                }
//                else
//                    throw new FoodNotFoundExp();
//            }
//            else
//                throw new RestaurantNotFoundExp();
//        }
//        else {
//            throw new FoodFromOtherRestaurantInCartExp();
//        }
//    }

    public void addToCart(Food food, Restaurant restaurant) throws FoodFromOtherRestaurantInCartExp, ExtraFoodPartyExp {
        boolean allowToAdd = false;
        if (customer.getCurrentOrder() == null) {
            Order newOrder = new Order(lastOrderId, restaurant);
            customer.setCurrentOrder(newOrder);
            allowToAdd = true;
            lastOrderId++;
        }
        else {
            if (restaurant.getId().equals(customer.currentOrder.getRestaurant().getId()))
                allowToAdd = true;
        }
        if (allowToAdd) {
            if (food instanceof PartyFood)
                customer.addPartyFoodToCurrentOrder((PartyFood) food);
            else
                customer.addFoodToCurrentOrder(food);
        }
        else
            throw new FoodFromOtherRestaurantInCartExp();
    }

    public String getCartJson() throws IOException {
        return customer.getCartJson();
    }

    public void finalizeOrder() throws NotEnoughCreditExp, ExtraFoodPartyExp {
        int cartPrice = customer.cartOverallPrice();
        Restaurant currentRestaurant = customer.getCurrentOrder().getRestaurant();
        if (cartPrice > customer.getCredit()) {
//            currentRestaurant.restorePreviousPartyCounts(customer.getCurrentOrder());
            customer.emptyCurrentOrder();

            throw new NotEnoughCreditExp();
        }
        currentRestaurant.reducePartyFoodAmounts(customer.getCurrentOrder());
        customer.getCurrentOrder().setStatus("finding delivery");

        Timer timer = new Timer();
        TimerTask task = new CouriersScheduler(customer.getCurrentOrder());
        timer.schedule(task, 0, 3000);

        customer.addCredit(-1 * cartPrice);
        customer.addOrder(customer.getCurrentOrder());
        customer.emptyCurrentOrder();
    }

    public String getRecommendedRestaurants() throws IOException {
        int numOfBests = 3;
        if (restaurants.size() < numOfBests)
            numOfBests = restaurants.size();
        Map<String, Float> bestRestaurants = getBestRestaurants(numOfBests);
        ObjectMapper mapperObj = new ObjectMapper();
        return mapperObj.writeValueAsString(bestRestaurants);
    }

    public ArrayList<Restaurant> getCloseRestaurants(float distance){
        ArrayList<Restaurant> closeRestaurants = new ArrayList<>();
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurants.get(i).getLocation().sendDistance() <= distance) {
                closeRestaurants.add(restaurants.get(i));
            }
        }
        return closeRestaurants;
    }

    public Restaurant getRestaurantById(String inId) throws NotFound404Exp {
        for (Restaurant restaurant: restaurants)
            if (restaurant.getId().equals(inId))
                return restaurant;
        throw new NotFound404Exp();
    }

    public void addCredit(int credit) {
        customer.addCredit(credit);
    }

//    public int getPrice(String foodName) throws BadRequest400Exp, NotFound404Exp, FoodNotFoundExp {
//        if (!customer.isRestaurantSet() || customer.getFoodCart().isEmpty())
//            throw new BadRequest400Exp();
//        Restaurant restaurant = getRestaurantById(customer.getRestaurantId());
//        int foodPrice = restaurant.sendFoodPriceByName(foodName);
//        return foodPrice;
//    }
//
//    public int getQuantity(String foodName) throws BadRequest400Exp, NotFound404Exp, FoodNotFoundExp {
//        if (!customer.isRestaurantSet() || customer.getFoodCart().isEmpty())
//            throw new BadRequest400Exp();
//        return customer.getFoodQuantity(foodName);
//    }

    public boolean isRestaurantInRange(String id, float distance) throws NotFound404Exp {
        Restaurant restaurant = getRestaurantById(id);
        return !(restaurant.getLocation().sendDistance() > distance);
    }

    public Food getFoodByName(String foodName, Restaurant restaurant) throws FoodNotFoundExp {
        for (Food food: restaurant.getMenu()) {
            if (food.getName().equals(foodName))
                return food;
        }
        throw new FoodNotFoundExp();
    }

    public PartyFood getPartyFoodByName(String foodName, Restaurant restaurant) {
        for (PartyFood partyFood: restaurant.getPartyFoods()) {
            if (partyFood.getName().equals(foodName))
                return partyFood;
        }
        return null;
    }

    public void deletePreviousPartyFoods(){
        for (Restaurant restaurant: restaurants) {
            if (restaurant.getPartyFoods().size() > 0) {
                restaurant.deletePartyFoods();
            }
        }
    }

    private void deletePreviousCustomerPartyFoods() {
        customer.clearCurrentPartyFoods();
    }

    public void addPartyRestaurants(ArrayList<Restaurant> partyRestaurants) {
        deletePreviousPartyFoods();
        deletePreviousCustomerPartyFoods();
//        System.out.println("new party: ");
        for (Restaurant restaurant: partyRestaurants) {
//            System.out.println(restaurant.getId());
            Restaurant currentRestaurant = null;
            try {
                currentRestaurant = getRestaurantById(restaurant.getId());
            } catch (NotFound404Exp notFound404Exp) {
                currentRestaurant = restaurant;
                restaurants.add(restaurant);
            }
            currentRestaurant.updateMenu();
        }
    }

    public ArrayList<Restaurant> getClosePartyRestaurants(float distance){
        ArrayList<Restaurant> closeRestaurants = getCloseRestaurants(distance);
        ArrayList<Restaurant> closePartyRestaurants = new ArrayList<>();
        for (Restaurant restaurant: closeRestaurants)
            if (restaurant.getPartyFoods().size() > 0)
                closePartyRestaurants.add(restaurant);
        return closePartyRestaurants;
    }
}