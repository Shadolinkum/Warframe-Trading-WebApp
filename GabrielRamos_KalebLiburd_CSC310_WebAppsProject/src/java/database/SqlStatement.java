/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import com.mysql.jdbc.Driver;

/**
 *
 * @author Gabriel
 */
public class SqlStatement {

    private Statement statement;

    public SqlStatement() {

        makeStatement();

    }

    public Statement makeStatement() {
        try {
            Connect c = new Connect();
            Connection conn = c.makeConnection();
            statement = conn.createStatement();
        } catch (SQLException e) {
        }
        return statement;
    }

    public int executeUpdate(String sqlStatement) {
        int rowCount = 0;
        try {
            rowCount = statement.executeUpdate(sqlStatement);
        } catch (SQLException e) {
        }
        return rowCount;
    }

    public ResultSet executeQuery(String sqlStatement) {
        ResultSet results = null;
        try {
            results = statement.executeQuery(sqlStatement);
        } catch (SQLException e) {
        }
        return results;
    }
}
