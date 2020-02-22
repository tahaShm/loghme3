<%@ page import="utils.Customer" %>
<%@ page import="utils.App" %>
<%@ page import="utils.Order" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Customer customer = App.getInstance().getCustomer();
    ArrayList<Order> orders = App.getInstance().getCustomer().getOrders();
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>User</title>
    <style>
        li {
            padding: 5px
        }
    </style>
</head>

<body>
<ul>
    <li>id: 1</li>
    <li>full name: <%=customer.getName()%></li>
    <li>phone number: <%=customer.getPhoneNumber()%></li>
    <li>email: <%=customer.getEmail()%></li>
    <li>credit: <%=customer.getCredit()%> Toman</li>
    <form action="" method="POST">
        <button type="submit">increase</button>
        <input type="text" name="credit" value="" />
    </form>
    <li>
        Orders :
        <ul>
            <%
                for (Order order: orders) {
            %>
            <li>
                <a href="/">order id : <%=order.getId()%></a>
            </li>
            <%
                }
            %>
        </ul>
    </li>
</ul>
</body>

</html>