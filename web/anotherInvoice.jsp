<%-- 
    Document   : addAnotherInvoice
    Created on : 04 30, 17, 4:07:05 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Invoice Data Saved</title>
    </head>
    <body>
          <%@include file="/WEB-INF/source/header-sidebar.jsp" %>
        
         <div id="content-wrapper">
            <div class="mui--appbar-height"></div>
            <div class="mui--appbar-height"></div>
            <div class="mui-container">
                <div class="mui-row">
                    <div class="mui-col-md-6 mui-col-md-offset-3">
                        <legend class="mui--text-center mui--text-display3">Invoice</legend>
        <c:set var="errorMessage" value="${requestScope.message}"/>
        <c:if test="${errorMessage ne '' && errorMessage ne null && errorMessage ne 'null'}">
            <p>${errorMessage}</p><br><br>
        </c:if>
        <c:if test="${message != ''}">
           <h1>${message}</h1>
        </c:if>
        <c:set var="invoiceID" value="${requestScope.invID}"/>   
        <a href="Servlets.getProductServlet?forOther=invoice">Create a new Invoice</a><br>
        <c:if test="${invoiceID ne '' && invoiceID ne null}">
            <a href="Servlets.viewInvoiceDetailsServlet?editInvoice=yes&invID=<c:out value="${invoiceID}"/>">Edit created Invoice</a><br>
            <a href="Servlets.viewInvoiceDetailsServlet?editInvoice=no&invID=<c:out value="${invoiceID}"/>">View Invoice Details</a><br><br>
        </c:if>
        <a href="Servlets.getInvoiceServlet">Edit another Invoice</a><br>
        <a href="unfinished.get?getTable=invoice">View Unfinished Invoices</a><br>
        <a href="province.get?whatFor=conditionsInvoice">Search Invoice</a><br>
        <a href="Servlets.getInvoiceServlet">View All Invoices</a><br>
        <br><br>
        <a href="notif.get">Return to Home</a>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
    </body>
</html>
