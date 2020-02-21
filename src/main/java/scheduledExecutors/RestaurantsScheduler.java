package scheduledExecutors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.App;
import utils.Restaurant;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class RestaurantsScheduler implements ServletContextListener {
    private ScheduledExecutorService scheduler;

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

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.schedule(new Callable() {
            public Object call() throws Exception {
                String loghmeBody = "";
                ArrayList<Restaurant> restaurants = null;
                App app = App.getInstance();
                ArrayList<Restaurant> convertedRestaurants;
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
                app.getCustomer().setId("1234");
                app.getCustomer().setName("Houman Chamani");
                app.getCustomer().setPhoneNumber("+989300323231");
                app.getCustomer().setEmail("hoomch@gmail.com");
                app.getCustomer().setCredit(100000);
                return 0;
            }
        }, 0, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
}
