package com.taorusb.consolecrundusesjdbc.repository;

import java.util.List;

public interface GenericRepository<T, ID extends Number> {

    T getById(ID id);

    void deleteById(ID id);

    List<T> findAll();

    T save(T entity);

    T update(T entity);

}