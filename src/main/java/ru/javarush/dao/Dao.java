package ru.javarush.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    List<T> findAll(int offset, int limit);
    int findAllCount();
    Optional<T> findById(int id);
    void saveOrUpdate(T t);
    void delete(T t);
}
