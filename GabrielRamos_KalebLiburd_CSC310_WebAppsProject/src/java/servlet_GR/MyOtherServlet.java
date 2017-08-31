/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet_GR;

//import database.DatabaseMain;
import database.SqlStatement;
import database.Connect;
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

/**
 *
 * @author Gabriel Ramos
 */
@WebServlet(name = "sign-up", urlPatterns = {"/sign-up"})
public class MyOtherServlet extends HttpServlet {

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
        SqlStatement statement = new SqlStatement();

// Extract the parameters
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        PrintWriter out = response.getWriter();
        //................................

//        if(username.equals("")){
//            String noBlankP = "Password cannot be blank!";
//            RequestDispatcher rd = request.getRequestDispatcher("ServletResponder");
//            rd.forward(request, response);
//        } else {
//            System.out.println("Valid password");
//        }
        int result = statement.executeUpdate("INSERT INTO members VALUES('" + username + "', '" + password + "', null , null , '" + email + "')");
        ResultSet searchResults = statement.executeQuery("SELECT username FROM  members WHERE username = '" + username + "' AND password = '" + password + "' AND email = '" + email + "' ");
        System.out.println("pass test data exec");
        if (result > 0) {
            /* TODO output your page here. You may use following sample code. */
            System.out.println("Record Inserted");
            try {
                if (searchResults.next()) {
                    searchResults.close();
                    out.println("<html>\n"
                            + "    <head>\n"
                            + "        <title>Registered!</title>\n"
                            + "        <meta charset=\"UTF-8\">\n"
                            + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                            + "    </head>\n"
                            + "    <body background=\"11570576_90x90.png.gif\">\n"
                            + "        <div style=\"color: blue; font-size: 200%\">Thank you for registering " + username + "!</div><br><br>\n"
                            + "     <A href=\"index.html\"><p style=\"font-size: 400%\">Log In Here!</p></A>"
                            + "     </body>\n"
                            + "</html>\n"
                            + "");

                    result = statement.executeUpdate("CREATE TABLE IF NOT EXISTS `myproject`.`" + username + "_inventory` (\n"
                            + "  `username` VARCHAR(45) NOT NULL DEFAULT '" + username + "',\n"
                            + "  `my_item_name` VARCHAR(45) NOT NULL,\n"
                            + "  `quantity` INT(45) NOT NULL,\n"
                            + "  INDEX `" + username + "_members_FK_idx` (`username` ASC),\n"
                            + "  PRIMARY KEY (`my_item_name`),\n"
                            + "  CONSTRAINT `" + username + "_members_FK`\n"
                            + "    FOREIGN KEY (`username`)\n"
                            + "    REFERENCES `myproject`.`members` (`username`)\n"
                            + "    ON DELETE CASCADE\n"
                            + "    ON UPDATE CASCADE)\n"
                            + "ENGINE = InnoDB\n"
                            + "DEFAULT CHARACTER SET = utf8;");
                    result = statement.executeUpdate("INSERT INTO " + username +"_inventory VALUES('" + username + "', 'Platinum', 0)");
                }
            } catch (SQLException e) {
            }

        } else {
            System.out.println("Insert Failed");
            try {
                if (searchResults.next()) {
                    searchResults.close();
                    out.println("<html>\n"
                            + "    <head>\n"
                            + "        <title>Already Registered!</title>\n"
                            + "        <meta charset=\"UTF-8\">\n"
                            + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                            + "    </head>\n"
                            + "    <body background=\"11570576_90x90.png.gif\">\n"
                            + "        <div style=\"color: blue; font-size: 200%\">Your username already exists " + username + "!</div><br><br>\n"
                            + "     <A href=\"sign-up.html\"><p style=\"font-size: 400%\">Please register with a new username!</p></A>"
                            + "     </body>\n"
                            + "</html>\n"
                            + "");
                } else {
                    searchResults = statement.executeQuery("SELECT username FROM  members WHERE username = '" + username + "' AND password = '" + password + "'");
                    if (searchResults.next()) {
                        searchResults.close();
                        out.println("<html>\n"
                                + "    <head>\n"
                                + "        <title>Already Registered!</title>\n"
                                + "        <meta charset=\"UTF-8\">\n"
                                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                                + "    </head>\n"
                                + "    <body background=\"11570576_90x90.png.gif\">\n"
                                + "        <div style=\"color: blue; font-size: 200%\">Your username already exists " + username + "!</div><br><br>\n"
                                + "     <A href=\"sign-up.html\"><p style=\"font-size: 400%\">Please register with a new username!</p></A>"
                                + "     </body>\n"
                                + "</html>\n"
                                + "");
                    } else {
                        searchResults = statement.executeQuery("SELECT username FROM  members WHERE username = '" + username + "'");
                        if (searchResults.next()) {
                            searchResults.close();
                            out.println("<html>\n"
                                    + "    <head>\n"
                                    + "        <title>Already Registered!</title>\n"
                                    + "        <meta charset=\"UTF-8\">\n"
                                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                                    + "    </head>\n"
                                    + "    <body background=\"11570576_90x90.png.gif\">\n"
                                    + "        <div style=\"color: blue; font-size: 200%\">Your username already exists " + username + "!</div><br><br>\n"
                                    + "     <A href=\"sign-up.html\"><p style=\"font-size: 400%\">Please register with a new username!</p></A>"
                                    + "     </body>\n"
                                    + "</html>\n"
                                    + "");
                        } else {
                            searchResults.close();
                            out.println("Could not insert SQL statement, SQL Syntax error likely.");
                        }
                    }
                }
                searchResults.close();
            } catch (SQLException e) {
                
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
