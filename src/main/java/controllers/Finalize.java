package controllers;

import utils.App;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/finalize")
public class Finalize extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        App app = App.getInstance();
        app.finalizeOrder();

        response.setStatus(200);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/profile.jsp");
        requestDispatcher.forward(request, response);
    }
}