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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
@WebServlet(name = "getAccountTypeStatus", urlPatterns = {"/getAccountTypeStatus"})
public class getAccountTypeStatusServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        ServletContext context = request.getSession().getServletContext();
        response.setContentType("text/html");
        
        try {
         Class.forName(context.getInitParameter("jdbcDriver"));
      } catch(ClassNotFoundException ex) {
         ex.printStackTrace();
         out.println("jdbc error: " + ex);
      }
        
        Connection conn = null;
        Statement stmt = null;
        
        try{
        //Allocate a database Connection object
         //This uses the pageContext servlet.  Look at Web.xml for the params!
         //This means we don't need to recompile!
         
         conn = DriverManager.getConnection(context.getInitParameter("databaseUrl"), context.getInitParameter("databaseUser"), context.getInitParameter("databasePassword"));
        
         //Allocate a Statement object within the Connection
         stmt = conn.createStatement();
         
         //---------------
         //THIS IS WHERE YOU START CHANGING
         //String accountType = ""+session.getAttribute("accountType");
         String preparedSQL = "select * from AccountStatus";
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ResultSet dbData = ps.executeQuery();
         ArrayList<accountStatusBean> accountStatusesRetrieved = new ArrayList<accountStatusBean>();
         //retrieve the information.
            while(dbData.next()){
               accountStatusBean asbean = new accountStatusBean();
                asbean.setAccountStatusID(dbData.getInt("accountStatusID"));
                asbean.setAccountStatusName(dbData.getString("accountStatusName"));
                accountStatusesRetrieved.add(asbean);
            }
         request.setAttribute("accountStatusesList", accountStatusesRetrieved);
         context.log(""+accountStatusesRetrieved.size());
         preparedSQL = "select * from AccountType";
         ps = conn.prepareStatement(preparedSQL);
         dbData = ps.executeQuery();
         ArrayList<accountTypeBean> accountTypesRetrieved = new ArrayList<accountTypeBean>();
         //retrieve the information.
            while(dbData.next()){
               accountTypeBean atbean = new accountTypeBean();
                atbean.setAccountTypeID(dbData.getInt("accountTypeID"));
                atbean.setAccountTypeName(dbData.getString("accountTypeName"));
                accountTypesRetrieved.add(atbean);
            }
         request.setAttribute("atypeList", accountTypesRetrieved);
         context.log(""+accountTypesRetrieved.size());
         request.setAttribute("message", ""+request.getAttribute("message"));
         
         if(request.getAttribute("account")!=null){
             request.setAttribute("message",""+request.getAttribute("message"));
             request.setAttribute("account", request.getAttribute("account"));
             request.getRequestDispatcher("editAccount.jsp").forward(request,response);
         }
         if((""+request.getParameter("forSearch")).equals("yes")){
             request.getRequestDispatcher("conditionsAccount.jsp").forward(request,response);
         }
         else{
            request.getRequestDispatcher("createAccount.jsp").forward(request,response);
         }
         
        }
        catch(Exception ex){
            ex.printStackTrace();
            //out.println("error: " + ex);
            String message = "Something went wrong. Error: "+ex;
            request.getRequestDispatcher("errorPage.jsp").forward(request,response);
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
                //out.println("Another SQL error: " + ex);
                String message = "Something went wrong. Error: "+ex;
                request.getRequestDispatcher("errorPage.jsp").forward(request,response);
            }
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
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
