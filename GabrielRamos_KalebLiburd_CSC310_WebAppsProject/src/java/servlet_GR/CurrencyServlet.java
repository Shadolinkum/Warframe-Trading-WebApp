/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet_GR;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import database.Connect;
import database.SqlStatement;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author Gabriel
 */
@WebServlet(name = "currency", urlPatterns = {"/currency"})
public class CurrencyServlet extends HttpServlet {

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
        //Checks the user's profile for the currency and reprints the whole html page for welcome.html
        //Looks through the database for the amount of currency currently held

        SqlStatement sqlstatement = new SqlStatement();
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        String nameCurrency = null;
        String quantCurrency = null;

        // Extract the parameters from the session instance for the user
        String username_from_session = (String) session.getAttribute("username");
        try {
            //find the name of the currency chosen for the application by looking through the database
            ResultSet currencyName = sqlstatement.executeQuery("SELECT item_name FROM items WHERE category = 'Currency'");

            //check if sql query found a currency name and save the name
            if (currencyName.next()) {
                currencyName.first();
                nameCurrency = currencyName.getString(1);
//                out.println("found currency");
            } else {
                out.println("no currency");
            }

            //find the amount of currency that the user owns by looking through the database
            ResultSet currencyAmount = sqlstatement.executeQuery("SELECT quantity FROM " + username_from_session + "_inventory WHERE my_item_name = '" + nameCurrency + "'");

            //check if sql query found money in the user's account and save the amount
            if (currencyAmount.next()) {
                currencyAmount.first();
                quantCurrency = currencyAmount.getString(1);
//                out.println("found money");
            } else {
                out.println("no money");
            }
            //print out the entire welcome.html page

            out.println("<!DOCTYPE html>\n"
                    + "<!--\n"
                    + "To change this license header, choose License Headers in Project Properties.\n"
                    + "To change this template file, choose Tools | Templates\n"
                    + "and open the template in the editor.\n"
                    + "-->\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title>Welcome</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "    </head>\n"
                    + "\n"
                    + "    <body background=\"Trinity&KubBackground.jpg\">\n"
                    + "    <center>\n"
                    + "        <h1 style=\"font-size: 600%\">Welcome to the welcome page!</h1>\n"
                    + "\n"
                    + "        <form action=\"BuyingServlet\" method=\"POST\">\n"
                    + "            <input type=\"submit\" value=\"Want to Buy\"><br><br><br>\n"
                    + "        </form>\n"
                    + "\n"
                    + "        <form action=\"TradeServlet\" method=\"POST\">\n"
                    + "            <input type=\"submit\" name=\"WTS\" value=\"WTS\"><br><br><br>\n"
                    + "        </form>\n"
                    + "\n"
                    + "        <form action=\"trade\" method=\"POST\">\n"
                    + "            <input type=\"submit\" value=\"Want to Trade\"><br><br><br>\n"
                    + "        </form>\n"
                    + "\n"
                    + "        <form action=\"login\" method=\"POST\">\n"
                    + "            <input type=\"submit\" name=\"session\" value=\"Log Out\"><br><br><br>\n"
                    + "        </form>\n"
                    + "        <br>\n"
                    + "        <br>\n"
                    + "        <form action=\"currency\" method=\"POST\">\n"
                    + "            <p>" + nameCurrency + " : " + currencyAmount.getString(1) + "</p>\n"
                    + "            <input type=\"submit\" value=\"Check Currency\">\n"
                    + "        </form>\n"
                    + "        \n"
                    + "    </center>\n"
                    + "</body>\n"
                    + "</html>");
            currencyName.close();
            currencyAmount.close();
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
