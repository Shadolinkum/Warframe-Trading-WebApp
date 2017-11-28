/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.mysql.jdbc.Driver;
import java.sql.*;

/**
 *
 * @author Gabriel
 */
public class Connect {

    public Connect() {
        makeConnection();
    }

    static final String DB_URL = "jdbc:mysql://localhost:3306/warframe_trading_app";
    static final String USER = "root";
    static final String PASS = "Sweetlove_32";

    private Connection connector;

    public Connection makeConnection() {
        try {
            if (connector == null) {
                new Driver();
                connector = DriverManager.getConnection(DB_URL, USER, PASS);
            }

        } catch (SQLException e) {
        }
        return connector;
    }

    public void closeConnection() {
        try {
            connector.close();
        } catch (SQLException e) {
        }
    }

}
