/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet_GR;

import database.SqlStatement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gabriel
 */
public class BuyingServlet extends HttpServlet {

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
        SqlStatement sqlstatement = new SqlStatement();

        //String items_to_buy = request.getParameter("item_to_buy");
        //String category_of_items = request.getParameter("category_of_items");
        
        HttpSession session = request.getSession(false);
        String username = (String)session.getAttribute("username");
        PrintWriter out = response.getWriter();
        ResultSet result = sqlstatement.executeQuery("SELECT * from market_items WHERE username NOT LIKE '"+username+"'");

        try {
            if (result.next()) {
                RequestDispatcher rd = request.getRequestDispatcher("WantToBuy.html");
                rd.include(request, response);
                out.println("<form name=\"ItemBuyingForm\" action=\"BoughtServlet\" method=\"POST\">"
                        + "<select name=\"ItemIDToBuy\">");
                //did this to reset the list of the results -->
                
                while(result.next()){
                            //add a unique primary key to market_items table so that this can work properly
                            out.println("<option value=\""+ result.getString(1) +"\">"+ result.getString(2) +", price:"+ result.getString(4) +", quantity:"+ result.getString(5) +"</option>");
                        }
                out.println("</select>"
                        + "<input type=\"submit\" value=\"Buy\"><br>"
                        + "</form>"
                        + "<A href=\"welcome.html\"><p style=\"font-size: 400%\">Back to Home</p></A>");
                result.close();
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("WantToBuy.html");
                rd.include(request, response);
                result.close();
                out.println("<p style=\"color: blue; font-size: 200%\">Sorry, no items listed at this time.</p><br>"
                        + "<A href=\"welcome.html\"><p style=\"font-size: 400%\">Back to Home</p></A>");
            }
            /* TODO output your page here. You may use following sample code. */
        } catch (SQLException e) {
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
