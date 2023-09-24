package server;

import java.sql.*;

public class database {
    static final String DB_URL = "jdbc:mysql://localhost:3306/shop";
    static final String USER = "root";
    static final String PASS = "";

    public static Connection DBConn;
    
    public static void Init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            DBConn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connected to DB!");
    }

    public static Connection getConnection() {
        return DBConn;
    }
}
