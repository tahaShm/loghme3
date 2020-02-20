package controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.App;
import utils.Restaurant;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

@WebServlet("/getRestaurants")
public class GetRestaurants extends HttpServlet {

    public String getUrlBody(String url) throws Exception {
        URL urlObj = new URL(url);
        Charset charset = Charset.forName("UTF8");
        URLConnection urlConnection = urlObj.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        urlConnection.getInputStream(), charset));
        String body = "", inputLine = "";

        while ((inputLine = in.readLine()) != null)
            body += inputLine;
        in.close();
        return body;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loghmeBody = "";
        ArrayList<Restaurant> restaurants = null;
        App app = App.getInstance();
        ArrayList<Restaurant> convertedRestaurants = app.getRestaurants();
        if (convertedRestaurants == null || convertedRestaurants.isEmpty()) {
            ObjectMapper nameMapper = new ObjectMapper();
            try {
                loghmeBody = getUrlBody("http://138.197.181.131:8080/restaurants");
                System.out.println(loghmeBody);
                restaurants = nameMapper.readValue(loghmeBody, ArrayList.class);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            convertedRestaurants = nameMapper.convertValue(restaurants, new TypeReference<ArrayList<Restaurant>>() { });
            app.setRestaurants(convertedRestaurants);
//            for (Restaurant restaurant: convertedRestaurants) {
//                System.out.println("id: " + restaurant.getId());
//                System.out.println("name: " + restaurant.getName());
//                System.out.println("location: " + restaurant.getLocation());
//                System.out.println("logo: " + restaurant.getLogo());
//          }
            app.getCustomer().setId("1234");
            app.getCustomer().setName("Houman Chamani");
            app.getCustomer().setPhoneNumber("+989300323231");
            app.getCustomer().setEmail("hoomch@gmail.com");
            app.getCustomer().setCredit(100000);
//            System.out.println("0, " + convertedRestaurants.get(0).getName());
        }
//        System.out.println("1, " + convertedRestaurants.get(0).getName());
        response.setContentType("text/html;charset=UTF-8");
        String pageName = "restaurants.jsp";
        response.setStatus(200);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(pageName);
        requestDispatcher.forward(request, response);
    }
}