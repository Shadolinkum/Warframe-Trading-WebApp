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
public class TransactionBuy extends HttpServlet {

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
        try {
            /* TODO output your page here. You may use following sample code. */
            SqlStatement sqlstatement = new SqlStatement();
            PrintWriter out = response.getWriter();
            String currency_name = (String) request.getAttribute("currency_name");
            String original_owner = (String) request.getAttribute("original_owner");
            String item_bought = (String) request.getAttribute("item_bought");
            String quantity = (String) request.getAttribute("quantity");
            String price = (String) request.getAttribute("price");
            int buyer_currency = 0;
            int result = 0;

            //finds the currency being used
            ResultSet resultSet = sqlstatement.executeQuery("SELECT item_name FROM items WHERE category = 'Currency'");
            resultSet.first();
            currency_name = resultSet.getString("item_name");

            //gets the username of the buyer, the item ID of the market item that was bought, and gets the amount of currency the buyer has
            HttpSession session = request.getSession(false);
            String buyer = (String) session.getAttribute("username");
            String ItemIDToBuy = (String) session.getAttribute("ItemIDToBuy");

            resultSet = sqlstatement.executeQuery("SELECT quantity FROM " + buyer + "_inventory WHERE  my_item_name = '" + currency_name + "'");
            resultSet.first();
            buyer_currency = resultSet.getInt("quantity");

            //deletes the item from the original user (or subtracts the quantity of the item sold)
            resultSet = sqlstatement.executeQuery("SELECT quantity FROM " + original_owner + "_inventory WHERE my_item_name = '" + item_bought + "'");
            resultSet.first();
            int quant_of_item_bought = resultSet.getInt("quantity");
            if (quant_of_item_bought > 1) {
                
                quant_of_item_bought -= Integer.parseInt(quantity);
                
                result = sqlstatement.executeUpdate("UPDATE " + original_owner + "_inventory\n"
                    + "SET quantity ='" + quant_of_item_bought + "'\n"
                    + "WHERE my_item_name = '" + item_bought + "'; ");
            } else {
            result = sqlstatement.executeUpdate("DELETE FROM " + original_owner + "_inventory\n"
                    + "WHERE my_item_name='" + item_bought + "'");
            }

            //adds the item to the buyer's inventory (or adds quantity of item bought)
            resultSet = sqlstatement.executeQuery("SELECT quantity FROM " + buyer + "_inventory WHERE my_item_name = '" + item_bought + "'");
            if (resultSet.first()) {
                quant_of_item_bought = resultSet.getInt("quantity");
                quant_of_item_bought += Integer.parseInt(quantity);
                result = sqlstatement.executeUpdate("UPDATE " + buyer + "_inventory\n"
                    + "SET quantity ='" + quant_of_item_bought + "'\n"
                    + "WHERE my_item_name = '" + item_bought + "'; ");
            } else {
                result = sqlstatement.executeUpdate("INSERT INTO " + buyer + "_inventory (my_item_name, quantity)\n"
                        + "VALUES ('" + item_bought + "','" + quantity + "')");
            }

            //subtracts the price from the buyer's currency
            int subtracted_price = subtractedQuantity(buyer_currency, price);

            result = sqlstatement.executeUpdate("UPDATE " + buyer + "_inventory\n"
                    + "SET quantity ='" + subtracted_price + "'\n"
                    + "WHERE my_item_name = '" + currency_name + "'; ");
            
            //adds currency to the original_owner of the item
            resultSet = sqlstatement.executeQuery("SELECT quantity FROM " + original_owner + "_inventory WHERE my_item_name = '" + currency_name + "'");
            resultSet.first();
            int owner_currency = resultSet.getInt("quantity");
            int added_price = addedQuantity(owner_currency, price);

            result = sqlstatement.executeUpdate("UPDATE " + original_owner + "_inventory\n"
                    + "SET quantity ='" + added_price + "'\n"
                    + "WHERE my_item_name = '" + currency_name + "'; ");

            RequestDispatcher rd = request.getRequestDispatcher("WantToBuy.html");
            rd.include(request, response);

            out.println("<h1 style=\"font-size: 600%\">Thank you for buying!</h1><br>"
                    //Not Safe to link to servlet
                    + "<A href=\"BuyingServlet\"><p style=\"font-size: 400%\">Buy Again?</p></A><br>"
                    + "<A href=\"welcome.html\"><p style=\"font-size: 400%\">Back to Home</p></A>");

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

    protected int subtractedQuantity(int inventory_currency, String market_price) {
        int new_market_price = Integer.parseInt(market_price);

        return inventory_currency - new_market_price;
    }
    
    protected int addedQuantity(int owner_currency, String market_price) {
        int new_market_price = Integer.parseInt(market_price);

        return owner_currency + new_market_price;
    }
}
