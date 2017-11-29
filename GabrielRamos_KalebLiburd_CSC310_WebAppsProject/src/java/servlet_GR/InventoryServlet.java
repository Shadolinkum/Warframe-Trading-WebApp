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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gabriel
 */
@WebServlet(name = "inventory", urlPatterns = {"/inventory"})
public class InventoryServlet extends HttpServlet {

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

        //retrieve the session of the current user
        HttpSession session = request.getSession(false);
        //store the username stored in session
        String username = (String) session.getAttribute("username");
        //prepare to print out html
        PrintWriter out = response.getWriter();
        //declare an sql statement
        SqlStatement sqlstatement = new SqlStatement();
        //store the name of the currency used
        String nameCurrency = null;

        try {
            //find the name of the currency chosen for the application by looking through the database
            ResultSet currencyName = sqlstatement.executeQuery("SELECT item_name FROM items WHERE category = 'Currency'");

            //check if sql query found a currency name and save the name
            if (currencyName.first()) {
                nameCurrency = currencyName.getString(1);
                //out.println("found currency");
            } else {
                out.println("no currency");
            }

            //find all the items in the user's inventory excluding currency
            ResultSet result = sqlstatement.executeQuery("SELECT * from " + username + "_inventory WHERE my_item_name NOT LIKE '"+nameCurrency+"'");

            if (result.first()) {

                //put the inventory on the same page but after all the html content has loaded
                RequestDispatcher rd = request.getRequestDispatcher("welcome.html");
                rd.include(request, response);

                //print out the inventory as a drop down box on the welcome page
                out.println("<center><form name=\"UserInventory\" action=\"InventoryServlet\" method=\"POST\">"
                        + "<select>"
                );
                //print out each row of the result set from the query into the drop down box
                do {
                    out.println("<option value=\"" + result.getString(1) + "\">" + result.getString(2) + ", Quantity: " + result.getString(3) + "</option>");
                } while (result.next());
                out.println("</select>"
                        + "</form</center>"
                );
                result.close();
            } else {
                //there are not items in your inventory
                RequestDispatcher rd = request.getRequestDispatcher("welcome.html");
                rd.include(request, response);

                result.close();

                //print under the check invetnory button a dropdown box that says you have no items
                out.println("<center><form name=\"UserInventory\" action=\"InventoryServlet\" method=\"POST\">"
                        + "<select>"
                        + "<option value=\"nothing\"> You have no items. </option>"
                        + "</select>"
                        + "</form>"
                        + "</center>"
                );
            }

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
