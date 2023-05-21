package kr.ac.hallym.onlinedataanalyser.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountRepository {
    void test() throws SQLException {
        System.loadLibrary("");
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306");
    }
}
