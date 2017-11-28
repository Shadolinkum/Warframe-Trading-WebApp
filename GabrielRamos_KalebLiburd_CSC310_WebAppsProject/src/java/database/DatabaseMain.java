/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Gabriel Ramos
 */
public class DatabaseMain {
    
    private Connection connection = null;
    Statement st;
    String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    String DB_URL = "jdbc:mysql://localhost/warframe_trading_app";
    String USER = "root";
    String PASS = "Sweetlove_32";
    
    public DatabaseMain() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
            st = (Statement) connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(st);
            System.out.println(connection);
        }
        
    }
    
    public int executeUpdate(String query) {
        int r = 0;
        try {
            r = st.executeUpdate(query);
        } catch (SQLException e) {}
        return r;
    }
    
    public ResultSet dataexecuteQuery(String query) {
        ResultSet result = null;
        try {
            System.out.println("Execute Query");
            result = st.executeQuery(query);
            System.out.println("Passed result exec query");
        } catch (SQLException e) {}
        return result;
    }
    
}
