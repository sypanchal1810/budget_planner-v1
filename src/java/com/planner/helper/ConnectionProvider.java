package com.planner.helper;

import java.sql.*;

public class ConnectionProvider {

    private static Connection con;

    public static Connection getConnection() {
        try {
            if (con == null) {
                // Load Driver Class
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Create a connection
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/budget_planner", "root", "admin1234");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
