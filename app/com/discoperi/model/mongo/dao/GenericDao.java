package com.discoperi.model.mongo.dao;

import java.util.List;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
public interface GenericDao<E> {

    void save(E entity);

    E findById(String id);

    List<E> findAll();

    void remove(E entity);

    void remove(String id);

}
