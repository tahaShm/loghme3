package controllers;

import org.apache.commons.lang.StringUtils;
import utils.App;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addCredit")
public class AddCredit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String toAdd = request.getParameter("credit");
        if (!StringUtils.isNumeric(toAdd) || Integer.valueOf(toAdd) <= 0) {
            response.setStatus(403);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/forbidden.jsp");
            requestDispatcher.forward(request, response);
        }
        App.getInstance().getCustomer().addCredit(Integer.valueOf(toAdd));
        response.setStatus(200);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/profile.jsp");
        requestDispatcher.forward(request, response);
    }
}