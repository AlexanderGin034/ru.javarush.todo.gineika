package ru.javarush.service;

import ru.javarush.entity.Status;

import java.util.List;

public interface ServiceDao<T> {
    List<T> findAll(int offset, int limit);

    int findAllCount();

    T update(int id, String description, Status status);

    T create(String description, Status status);

    void delete(int id);
}
