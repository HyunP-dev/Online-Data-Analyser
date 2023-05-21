package kr.ac.hallym.onlinedataanalyser.repository;

public interface IRepository<T> {
    long count();

    void delete(T entity);

    void deleteAll();

    Iterable<T> findAll();

    void save(T entity);

    void saveAll(Iterable<T> entities);
}
