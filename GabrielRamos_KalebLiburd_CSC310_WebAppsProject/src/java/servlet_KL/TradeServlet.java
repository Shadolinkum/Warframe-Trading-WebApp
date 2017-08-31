/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet_KL;

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
import database.SqlStatement;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Killua
 */
@WebServlet(name = "TradeServlet", urlPatterns = {"/TradeServlet"})
public class TradeServlet extends HttpServlet {

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
        //database object
        SqlStatement sqlstatement = new SqlStatement();

        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        PrintWriter testerout = response.getWriter();
        //get parameters
        String value = request.getParameter("mySelect");
        String query = "";
        //out.println(value);

        //dynamically make the categories list
        if (request.getParameter("WTS").equals("WTS")) {
            ResultSet result = sqlstatement.executeQuery("SELECT * FROM categories");

            try (PrintWriter out = response.getWriter()) {

                RequestDispatcher rd = request.getRequestDispatcher("WantToSell.html");
                rd.include(request, response);

                out.println("<br>"
                        + "<center>"
                        + "<form name=\"category_list\" action=\"TradeServlet\" method=\"post\">"
                        + "<select id=\"mySelect\" name=\"mySelect\">");
                while (result.next()) {
                    out.println("<option value=\"" + result.getString(1) + "\">" + result.getString(1) + "</option>");
                }
                out.println("</select><br><br>"
                        + "<input type=\"submit\" name=\"WTS\" value=\"item_categories\">"
                        + "</form>"
                        + "</center>");

            } catch (SQLException e) {
            }

        }

        //WTS button(want to sell)
        //dynamically make the list of items of chosen category
        if (request.getParameter("WTS").equals("item_categories")) {
            try {

                ResultSet result_cat = sqlstatement.executeQuery("SELECT * FROM categories");

                try (PrintWriter out = response.getWriter()) {

                    RequestDispatcher rd = request.getRequestDispatcher("WantToSell.html");
                    rd.include(request, response);

                    //_____________________________________
                    out.println("<br>"
                            + "<center>"
                            + "<form name=\"category_list\" action=\"TradeServlet\" method=\"post\">"
                            + "<select id=\"mySelect\" name=\"mySelect\">");
                    while (result_cat.next()) {
                        out.println("<option value=\"" + result_cat.getString(1) + "\">" + result_cat.getString(1) + "</option>");
                    }
                    out.println("</select><br><br>"
                            + "<input type=\"submit\" name=\"WTS\" value=\"item_categories\">"
                            + "</form>"
                            //__________________________________

                            + "<br>"
                            + "<form name=\"post_sale\" action=\"TradeServlet\" method=\"post\">"
                            + "<select name=\"item\">");
                    ResultSet result = sqlstatement.executeQuery("SELECT item_name FROM items WHERE category='" + value + "'");
                    while (result.next()) {
                        out.println("<option value=\"" + result.getString(1) + "\">" + result.getString(1) + "</option>");
                    }
                    out.println("</select><br><br>"
                            + "Price: <br><input type=\"text\" name=\"price\"><br><br>"
                            + "Quantity: <br><input type=\"text\" name=\"quantity\"><br><br>"
                            + "<input type=\"submit\" name=\"WTS\" value=\"Post Sale\">"
                            + "</form>"
                            + "</center>");

                }
            } catch (SQLException e) {
            }
        }

        String item = request.getParameter("item");
        String price = request.getParameter("price");
        String quantity = request.getParameter("quantity");
        PrintWriter out = response.getWriter();

        if (request.getParameter("WTS").equals("Post Sale")) {
            //say sale was posted successfully
            RequestDispatcher rd = request.getRequestDispatcher("welcome.html");
            rd.include(request, response);

            //insert into the table
            query = "INSERT INTO market_items (market_item_name, username, price, quantity) VALUES ('" + item + "', '" + username + "' ,'" + price + "','" + quantity + "')";
            int posted = sqlstatement.executeUpdate(query);
            out.println("<center><p style=\"font-size: 400%\">Posted Successfully</p></center>");
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
