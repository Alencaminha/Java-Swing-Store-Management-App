package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    static final String dataBaseURL = "jdbc:mysql://localhost:3306/swing_store_database";
    static final String username = "root";
    static final String password = "@lErootsenha918156789";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dataBaseURL, username, password);
    }
}
