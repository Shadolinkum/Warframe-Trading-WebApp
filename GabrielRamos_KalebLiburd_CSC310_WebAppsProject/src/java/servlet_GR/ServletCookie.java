/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet_GR;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gabriel
 */
public class ServletCookie extends HttpServlet {

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
        String u = request.getParameter("username");
        String p = request.getParameter("password");
        PrintWriter out = response.getWriter();

        if (u.equals("shadolinkum") && p.equals("sweetlove")) {
            //check for cookies, if not found, make new cookie, if found, retrieve them
            Cookie nameCookie = null;
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                nameCookie = new Cookie("username", u);
                nameCookie.setMaxAge(60 * 2);

                response.addCookie(nameCookie);

                out.println("<h1>Welcome " + u + "!</h1>");
            } else {
                boolean found = false;
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("username")) {
                        out.println("<h1>Welcome back " + cookies[i].getValue() + "<!/h1>");
                        found = true;
                    }
                    if (found) {
                        //username cookie was found
                    } else {
                        //this means cookies were found but it was not the cookie i wanted to find
                        out.println("Error finding username cookie!");
                    }
                }
            }
        } else {
            out.println("Username or password error!");
            response.sendRedirect("index.html");
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
