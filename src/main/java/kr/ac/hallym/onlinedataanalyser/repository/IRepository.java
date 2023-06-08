package kr.ac.hallym.onlinedataanalyser.repository;

import java.sql.SQLException;

public interface IRepository<T> {
    long count() throws SQLException;

    void delete(T entity) throws SQLException;

    void deleteAll() throws SQLException;

    Iterable<T> findAll() throws SQLException;

    void save(T entity) throws SQLException;

    void saveAll(Iterable<T> entities) throws SQLException;
}
