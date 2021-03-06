<%-- 
    Document   : getAccount
    Created on : 09 10, 16, 12:40:05 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    ArrayList<accountBean> accountsList = (ArrayList<accountBean>)request.getAttribute("accountsList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Account List</title>
    </head>
    <body>
        <h1>This is the Accounts list!</h1>
        
        <table border="1">
            <tr>
                <th>Account ID</th>
                <th>User Name</th>
                <th>Account Type</th>
                <th>Account Status</th>
            </tr>
        
        <c:forEach items="${accountsList}" var="acc" begin="0" step="1" varStatus="status">
            <tr>
                    <td>${acc.getAccountID()}</td>
                    <td>${acc.getUserName()}</td>
                    <td>${acc.getAccountType()}</td>
                    <td>${acc.getAccountStatus()}</td>
                    <td><a href="account.getDetails?accID=<c:out value="${acc.getAccountID()}"/>">Edit</td>
            </tr>
            
        </c:forEach>
        </table>
        <br><br>
        
        <c:if test="${forInvoice eq 'yes'}">
            <br><br>
            <a href="viewCart.jsp">View Cart</a> to add your invoice.
        </c:if>
        
        
        
        
        <a href="homePage.jsp">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    
    </body>
</html>
