package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_PATH = "resources/database/traveltour.accdb";
    private static final String URL = "jdbc:ucanaccess://" + DB_PATH;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
