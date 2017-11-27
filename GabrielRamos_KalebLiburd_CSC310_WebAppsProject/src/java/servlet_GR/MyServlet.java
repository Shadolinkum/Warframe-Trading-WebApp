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
//import database.DatabaseMain;

/**
 *
 * @author Gabriel Ramos
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class MyServlet extends HttpServlet {

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
        //Create an object  of type database....  
//        DatabaseMain db = new DatabaseMain();
        SqlStatement sqlstatement = new SqlStatement();
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        //if it doesnt exist, make it
        if (session == null) {
            //check if session exists.   

// Extract the parameters
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            //................................             
            ResultSet result = sqlstatement.executeQuery("SELECT email FROM  members WHERE username = '" + username + "' AND password = '" + password + "'");
            System.out.println("pass test data exec");
            try {

                if (result.next()) {
                    System.out.println("Found It");
                    result.close();
                    RequestDispatcher rd = request.getRequestDispatcher("welcome.html");

                    out.println("<center><p style=\"font-size: 400%\">Thank you for signing in " + username + "!</p></center>");

                    session = request.getSession();
                    session.setAttribute("username", username);
                    //session.setMaxInactiveInterval(2*60);
                    //would set max inactive time to 2 minutes
                    rd.include(request, response);

                } else {
                    result = sqlstatement.executeQuery("SELECT email FROM  members WHERE username = '" + username + "'");
                    if (result.next()) {
                        System.out.println("Found It");
                        RequestDispatcher rd = request.getRequestDispatcher("errorpage.html");
                        result.close();

                        out.println("<p style=\"font-size: 600%\"><center>Incorrect password, sorry " + username + ".</center></p>");

                        rd.include(request, response);
                    } else {
                        System.out.println("Didn't Find It");
                        result.close();
                        RequestDispatcher rd = request.getRequestDispatcher("errorpage.html");
                        out.println("<center><p style=\"font-size: 600%\">Wrong username.</p></center>");

                        rd.include(request, response);

                    }
                }
            } catch (SQLException | IOException e) {
            }
        } else if (request.getParameter("session").equals("Log In")) {
            //If session already exists
            RequestDispatcher rd = request.getRequestDispatcher("welcome.html");
            String username_from_session = (String) session.getAttribute("username");
            out.println("<p style=\"font-size: 600%\">You've already signed in " + username_from_session + "!</p>");

            rd.include(request, response);

        } else if (request.getParameter("session").equals("Log Out")) {
            session.invalidate();
            response.sendRedirect("index.html");
        }

    }

    public boolean existingUserCheck(String email, String studentid, HttpServletResponse response) {

        return false;
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
