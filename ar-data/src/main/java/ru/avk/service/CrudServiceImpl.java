package ru.avk.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by ipopkov on 02/04/16.
 */
public abstract class CrudServiceImpl<T, ID extends Serializable>  {

    protected JpaRepository<T, ID> entityRepository;

    public <S extends T> S save(S entity) {
        return entityRepository.save(entity);
    }

    public T findOne(ID id){
        return entityRepository.findOne(id);
    }

    public boolean exists(ID id){
        return entityRepository.exists(id);
    }

    public List<T> findAll(){
        return entityRepository.findAll();
    }

    public void delete(T entity){
        entityRepository.delete(entity);
    }

    public void deleteAll(){
        entityRepository.deleteAll();
    }
}
