/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */
@WebServlet(name = "createRestockOrderServlet", urlPatterns = {"/createRestockOrderServlet"})
public class createRestockOrderServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
         Class.forName(context.getInitParameter("jdbcDriver"));
        } catch(ClassNotFoundException ex) {
         ex.printStackTrace();
         out.println("jdbc error: " + ex);
      }
        
        Connection conn = null;
        Statement stmt = null;
        
        try{
            
         conn = DriverManager.getConnection(context.getInitParameter("databaseUrl"), context.getInitParameter("databaseUser"), context.getInitParameter("databasePassword"));
        
         //Allocate a Statement object within the Connection
         stmt = conn.createStatement();
         
         //---------------
         //THIS IS WHERE YOU START CHANGING
         
         String preparedSQL = "insert into RestockOrder(restockDateCreated, restockArriveDate, restockCompletedDate, restockCost, supplier) values(?,?,?,?,?)";

             
         //you don't change this
         PreparedStatement rs = conn.prepareStatement(preparedSQL);
         
         java.util.Date myDate = new java.util.Date("10/10/2009");
         java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
         
         
         String inputDateCreated = request.getParameter("restockDateCreatedInput");
         String inputArriveDate = request.getParameter("restockDateArriveDateInput");
         String inputCompletedDate = request.getParameter("restockCompletedDateInput");
         Float inputCost = Float.parseFloat(request.getParameter("restockCostInput"));
         String inputSupplier= request.getParameter("supplierInput");
         
         
         rs.setString(1,inputDateCreated);
         rs.setString(2,inputArriveDate);
         rs.setString(3,inputCompletedDate);
         rs.setFloat(4,inputCost);
         rs.setString(5,inputSupplier);
         rs.executeUpdate();                   //at this point, you have already inserted into the database
         
         String message = "Account successfully created!";
         request.setAttribute("message", message);
         request.getRequestDispatcher("homePage.jsp").forward(request,response);
         
         
         
         
         
        
         
        }
         catch(Exception ex){
            ex.printStackTrace();
            out.println("SQL error: " + ex);
        }
        finally {
            out.close();  // Close the output writer
            try {
              //Close the resources
              if (stmt != null) stmt.close();
              if (conn != null) conn.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                out.println("Another SQL error: " + ex);
            }
     }
    
    
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
