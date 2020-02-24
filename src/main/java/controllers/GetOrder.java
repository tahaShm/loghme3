package controllers;

import org.apache.commons.lang.StringUtils;
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
        String StrId = queryString.substring(queryString.indexOf('=') + 1);
        if (!StringUtils.isNumeric(StrId)) {
            response.setStatus(403);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/forbidden.jsp");
            requestDispatcher.forward(request, response);
        }
        int id = Integer.valueOf(StrId);
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