package ru.avk.service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ipopkov on 02/04/16.
 */
public interface CrudService<T,  ID extends Serializable> {
    
    <S extends T> S save(S entity);

    T findOne(ID entity);

    boolean exists(ID entity);

    List<T> findAll();

    void delete(T entity);

    void deleteAll();
}
