package kr.ac.hallym.onlinedataanalyser.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed())
                return connection;
        } catch (SQLException ignored) { }
        String url = "jdbc:mariadb://localhost:3306/online_data_analyser";
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, "online_data_analyser_admin", "");
        } catch (SQLException | ClassNotFoundException exception) {
            System.err.println(exception.getMessage());
            return null;
        }
        return connection;
    }

    private Database() {
    }
}
