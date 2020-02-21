package controllers;

import utils.App;
import utils.exceptions.NotFound404Exp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.ScheduledExecutorService;

@WebServlet("/restaurant/*")
public class GetRestaurant extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        App app = App.getInstance();

        StringTokenizer tokenizer = new StringTokenizer(request.getRequestURI(), "/");
        String command = tokenizer.nextToken();
        String id = tokenizer.nextToken();

        try {
            if (app.isRestaurantInRange(id, 170)) {
                response.setStatus(200);
                request.setAttribute("id", id);
            }
            else {
                response.setStatus(403);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/forbidden.jsp");
                requestDispatcher.forward(request, response);
            }
        }
        catch (NotFound404Exp e) {
            response.setStatus(404);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/notFound.jsp");
            requestDispatcher.forward(request, response);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/restaurant.jsp");
        requestDispatcher.forward(request, response);
    }
}
