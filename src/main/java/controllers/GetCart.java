package controllers;

import utils.App;
import utils.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart")
public class GetCart extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(200);
        Order currentOrder = App.getInstance().getCustomer().getCurrentOrder();
        if (currentOrder == null || (currentOrder.getFoods().size() == 0 && currentOrder.getPartyFoods().size() == 0)) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/emptyCart.jsp");
            requestDispatcher.forward(request, response);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/cart.jsp");
        requestDispatcher.forward(request, response);
    }
}