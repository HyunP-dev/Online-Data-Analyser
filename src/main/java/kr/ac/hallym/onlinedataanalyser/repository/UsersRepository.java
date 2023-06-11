package kr.ac.hallym.onlinedataanalyser.repository;

import kr.ac.hallym.onlinedataanalyser.database.Database;
import kr.ac.hallym.onlinedataanalyser.model.User;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;


public class UsersRepository implements IRepository<User> {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UsersRepository usersRepository = new UsersRepository();
//        usersRepository.saveAll(List.of(new User[]{
//                new User("alice", "1234", "alice")
//        }));
        usersRepository.deleteAll();
        System.out.println(usersRepository.findAll());
    }

    @Override
    public long count() throws SQLException {
        return ((ArrayList<User>) findAll()).size();
    }

    @Override
    public void delete(User entity) throws SQLException {
        Connection connection = Database.getConnection();

        assert connection != null;
        PreparedStatement query = connection.prepareStatement("DELETE FROM users WHERE userid=?");
        query.setString(1, entity.getUserid());
        query.execute();
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection connection = Database.getConnection();

        assert connection != null;
        connection.createStatement().execute("TRUNCATE TABLE users");
    }

    public Optional<User> findByUserid(String userid) throws SQLException {
        Connection connection = Database.getConnection();

        assert connection != null;
        Statement query = connection.createStatement();

        // TODO: Prevent SQL Injection
        ResultSet rs = query.executeQuery("SELECT * FROM users WHERE userid='"+userid+"'");
        return rs.next() ? Optional.of(new User(
                rs.getString("userid"),
                rs.getString("userpw"),
                rs.getString("nickname")
        )): Optional.empty();
    }

    @Override
    public Iterable<User> findAll() throws SQLException {
        Connection connection = Database.getConnection();

        assert connection != null;
        Statement query = connection.createStatement();
        ResultSet rs = query.executeQuery("SELECT * FROM users");
        ArrayList<User> results = new ArrayList<>();
        while (rs.next()) {
            results.add(
                    new User(
                            rs.getString("userid"),
                            rs.getString("userpw"),
                            rs.getString("nickname")
                    )
            );
        }
        return results;
    }

    @Override
    public void save(User entity) throws SQLException {
        Connection connection = Database.getConnection();

        assert connection != null;
        PreparedStatement query = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?)");

        query.setString(1, entity.getUserid());
        query.setString(2, entity.getUserpw());
        query.setString(3, entity.getNickname());
        query.execute();
    }

    @Override
    public void saveAll(Iterable<User> entities) throws SQLException {
        Connection connection = Database.getConnection();

        assert connection != null;
        PreparedStatement query = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?)");

        for (User entity : entities) {
            query.setString(1, entity.getUserid());
            query.setString(2, entity.getUserpw());
            query.setString(3, entity.getNickname());
            query.execute();
        }
    }
}
