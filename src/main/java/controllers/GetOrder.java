package controllers;

import utils.App;
import utils.exceptions.OrderNotFound;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order")
public class GetOrder extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryString = request.getQueryString();
        int id = Integer.valueOf(queryString.substring(queryString.indexOf('=') + 1));

        try {
            App.getInstance().getCustomer().getOrderById(id);
        }
        catch (OrderNotFound e) {
            response.setStatus(404);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/notFound.jsp");
            requestDispatcher.forward(request, response);
        }
        request.setAttribute("id", id);
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(200);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/order.jsp");
        requestDispatcher.forward(request, response);
    }
}