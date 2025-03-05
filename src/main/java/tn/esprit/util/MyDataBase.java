package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MyDataBase {
    private static MyDataBase instance;
    final String URL = "jdbc:mysql://127.0.0.1:3306/easy_way";
    final String USERNAME = "root";
    final String PASSWORD = "";
    private static Connection cnx;

    public MyDataBase() {
        try {
            cnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static MyDataBase getInstance() {
        if (instance == null)
            instance = new MyDataBase();
        return instance;
    }

    public static Connection getCnx() {
        return cnx;
    }


}
