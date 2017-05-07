<%-- 
    Document   : getRestockOrder
    Created on : 11 13, 16, 1:38:29 AM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII View RO</title>
    </head>
    <body>
        <h1>This is the Get Restock Order page!</h1>
        
        <c:set var="accountType" value="${sessionScope.accountType}"/>
        <c:set var="restocksList" value="${requestScope.restocksList}"/>
        <c:set var="listSize" value="${restocksList.size()}"/>
        <c:if test="${listSize > 0}">
            <table border="1">
                <tr>
                    <th>Restock Order Name</th>
                    <th>Date Due</th>
                    <th>Status</th>
                    <th>Date Delivered</th>
                    <th>Amount Paid</th>
                    <th>Discount</th>
                    <th>Date Paid</th>
                    <th>Date Created</th>
                </tr>

            <c:forEach items="${restocksList}" var="ro" begin="0" step="1" varStatus="status">
                <tr>
                    <td><a href="restockOrder.getDetails?restockID=<c:out value="${ro.getRestockOrderID()}"/>">${ro.getRestockOrderName()}</a></td>
                    <td>${ro.getRODateDue()}</td>
                    <td>${ro.getStatusName()}</td>
                    <td>${ro.getRODateDelivered()}</td>
                    <td>${ro.getAmountPaid()}</td>
                    <td>${ro.getDiscount()}</td>
                    <td>${ro.getDatePaid()}</td>
                    <td>${ro.getDateCreated()}</td>
                    <c:if test="${accountType eq '4' || accountType eq '5' || accountType eq '1'}">
                        <td><a href="restockOrder.getDetails?editRestock=yes&restockID=<c:out value="${ro.getRestockOrderID()}"/>">Edit</a></td>
                    </c:if>
                </tr>

            </c:forEach>
            </table>
        </c:if>
        
        <c:if test="${listSize eq 0}">
            <p> 0 restock orders found.</p>
        </c:if>
            
        <br><br>
        <a href="restockOrder.getStatus">Search RO</a><br>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
        
    </body>
</html>
