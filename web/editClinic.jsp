<%-- 
    Document   : editClinic
    Created on : 12 4, 16, 5:24:53 PM
    Author     : user
--%>

<%@page import="Beans.*,java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String accountType = ""+session.getAttribute("accountType");
    clinicBean clinic = (clinicBean)request.getAttribute("clinic");
    ArrayList<provinceBean> provList = (ArrayList<provinceBean>)request.getAttribute("provList");
    String message = ""+request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PESCII Edit Clinic</title>
    </head>
    <body>
        <h1>This is the Edit Clinic Page!</h1>
        
        <c:if test="${message != ''}">
            <p>${message}</p><br><br>
        </c:if>
        
        <form action="customer.editClinic" method="post">
                Clinic ID: <input type="hidden" value="${clinic.getClinicID()}" name="clinID">${clinic.getClinicID()}<br>
                PRCID: ${clinic.getPRCID()}<br>
                Clinic Name: <input type="text" value="${clinic.getClinicName()}" name="clinicNameInput" maxlength="255"><br>
                Clinic Address: <input type="text" value="${clinic.getClinicAddress()}" name="clinicAddressInput" maxlength="255"><br>
                Clinic Phone Number: <input type="text" value="${clinic.getClinicPhoneNumber()}" name="clinicPhoneNumberInput" maxlength="15"><br>
                <b>Province</b><br>
                From: ${clinic.getProvinceName()}<br>
                To:<select name="chosenProvince">
                    <c:forEach items="${provList}" var="pro" begin="0" step="1">
                        <option value="${pro.getProvinceID()}">${pro.getProvinceName()}</option>
                    </c:forEach>
                </select><br><br>
                <br><input type="submit" value="Save Changes">
                
        </form>
        
        <br><br>
        <c:choose>
            <c:when test="${accountType eq 3}">
                <a href="notif.get?forWhat=invoice">Return to Home</a>
            </c:when>
            <c:when test="${(accountType eq 4) || (accountType eq 5)} ">
                <a href="notif.get?forWhat=restock">Return to Home</a>
            </c:when>
            <c:when test="${accountType eq 1}">
                <a href="notif.get?forWhat=both">Return to Home</a>
            </c:when>
            <c:when test="${(accountType ne 3) || (accountType ne 4) || (accountType ne 5) || (accountType ne 1)}">
                <a href="homePage.jsp">Return to Home</a>
            </c:when>
        </c:choose>
        <br><br>
        <a href="Servlets.logoutServlet">logout</a>
        
    </body>
</html>