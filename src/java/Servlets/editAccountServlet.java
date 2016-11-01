package Servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(name = "editAccountServlet_1", urlPatterns = {"/editAccountServlet_1"})
public class editAccountServlet extends HttpServlet {

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
        
        PrintWriter out = response.getWriter();
        
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
         String preparedSQL = "update Account set userName=?, password=?, accountStatus=?, accountType=? where accountID=?";
         
         String newUserName = request.getParameter("userNameInput");
         String password = request.getParameter("newPasswordInput");
         String password2 = request.getParameter("newPasswordInput2");
         if(!password.equals(password2)){
             context.log("It didn't match!");
             request.setAttribute("message","Passwords did not match. No changes made.");
             request.getRequestDispatcher("homePage.jsp").forward(request,response);
             return;
         }
         if(password==null || password==""){password = request.getParameter("oldPassword");}
         else{
             try{

                //Salts for the hashing
                String[] salts = new String[10];
                salts[0] = "7LsDFJ9oHjDnfUr12";
                salts[1] = "K8oMilIOi0ji43amS";
                salts[2] = "AFIOUVAJNONVASJja";
                salts[3] = "nVaWIdsj19Aij63df";
                salts[4] = "uahRksD47kljnJN9k";
                salts[5] = "dMna7sY01jfIoaPlY";
                salts[6] = "Wg480ioAjEdsf31Ka";
                salts[7] = "gMutRHj70ubQnjB67";
                salts[8] = "gnQiaOhfXquh82z74";
                salts[9] = "mKvqn7834wHjk1kLa";

                Random rand = new Random();

                password = password + salts[rand.nextInt(10)];

                    //              End Salting

                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(password.getBytes());

                byte byteData[] = md.digest();

                StringBuffer sb = new StringBuffer();

                for(int i = 0; i < byteData.length; i++){
                    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                }

                password = sb.toString();
                }
            catch(NoSuchAlgorithmException e){
                e.printStackTrace();
            }

         }
         int newAccountStatus = Integer.parseInt(request.getParameter("accountStatusInput"));
         int newAccountType = Integer.parseInt(request.getParameter("accountTypeInput"));
         int accountID = Integer.parseInt(request.getParameter("accountIDInput"));
         
         
         
         
         PreparedStatement ps = conn.prepareStatement(preparedSQL);
         ps.setString(1,newUserName);
         ps.setString(2,password);
         ps.setInt(3,newAccountStatus);
         ps.setInt(4,newAccountType);
         ps.setInt(5,accountID);
         
         ps.executeUpdate();
         
         context.log("--->Account successfully updated. AccountID is: "+accountID);
         
         request.setAttribute("message", "Account successfully editted!");
         request.getRequestDispatcher("homePage.jsp").forward(request,response);
         
        }
        catch(SQLException ex){
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
