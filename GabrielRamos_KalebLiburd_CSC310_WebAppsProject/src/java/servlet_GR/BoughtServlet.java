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
public class BoughtServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        String ItemIDToBuy = request.getParameter("ItemIDToBuy");
        session.setAttribute("ItemIDToBuy", ItemIDToBuy);

        //gets the username of the original owner, the name of the bought item, the price of the item (in Currency), and the quantity of the item bought
        //and sets it as a request attribute to be used by the servlet being forwarded to
        try {
            PrintWriter out = response.getWriter();
            ResultSet resultSet = sqlstatement.executeQuery("SELECT username, market_item_name, quantity, price FROM market_items WHERE  market_ID = '" + ItemIDToBuy + "'");
            resultSet.first();
            request.setAttribute("original_owner", resultSet.getString("username"));
            request.setAttribute("item_bought", resultSet.getString("market_item_name"));
            request.setAttribute("quantity", resultSet.getString("quantity"));
            request.setAttribute("price", resultSet.getString("price"));
            resultSet.close();

            int price = Integer.parseInt((String)request.getAttribute("price")) ;
            //find currency used
            ResultSet resultSet2 = sqlstatement.executeQuery("SELECT item_name FROM items WHERE category = 'Currency'");
            resultSet2.first();
            String currency_name = resultSet2.getString("item_name");
            resultSet2.close();
            //find amount of currency of buyer
            ResultSet resultSet3 = sqlstatement.executeQuery("SELECT quantity FROM " + username + "_inventory WHERE  my_item_name = '" + currency_name + "'");
            resultSet3.first();
            int owner_currency = resultSet3.getInt("quantity");
            resultSet3.close();
            //check if buyer has enough money to buy item
            if (owner_currency - price < 0) {
                //if he doesn't, then return to buying page
                out.println("<h1 style=\"font-size: 600%\">Not enough " + currency_name + "!</h1><br>");

                RequestDispatcher rd = request.getRequestDispatcher("BuyingServlet");
                rd.include(request, response);

            } else {
                //if he does then continue as normal
                int result = sqlstatement.executeUpdate("DELETE FROM market_items\n"
                        + "WHERE market_ID='" + ItemIDToBuy + "'");

                if (result > 0) {
                    RequestDispatcher rd = request.getRequestDispatcher("TransactionBuy");
                    rd.forward(request, response);

                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("WantToBuy.html");
                    rd.include(request, response);
                    out.println("<p style=\"color: blue; font-size: 200%\">Sorry, your transaction could not be completed.</p><br>"
                            + "<A href=\"welcome.html\"><p style=\"font-size: 400%\">Back to Home</p></A>");
                }
                /* TODO output your page here. You may use following sample code. */
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
