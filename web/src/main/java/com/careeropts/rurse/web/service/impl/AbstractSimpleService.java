package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.web.exception.ClientErrorException;
import com.careeropts.rurse.web.exception.NotFoundException;
import com.careeropts.rurse.web.service.ISimpleService;
import com.google.common.base.Function;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Iterables.transform;


public abstract class AbstractSimpleService<T, U> implements ISimpleService<T> {

    IBaseDao<U> dao;

    protected abstract U toDatabaseObject (T model);
    protected abstract T fromDatabaseObject(U dataObject);
    protected abstract void normalizeAndValidate(T model);


    @Override
    public T getSingle(Long id) {
        if (id == null)
            throw new NotFoundException();

        U item = dao.getSingle(id);

        if (item == null)
            throw new NotFoundException();


        return fromDatabaseObject(item);
    }

    @Override
    public Iterable<T> query(String searchText, Integer pageNum, Integer perPage) {
        if (pageNum == null || pageNum < 0)
            pageNum = 0;

        if (perPage == null || perPage < 0)
            perPage = Integer.MAX_VALUE;

        Iterable<U> results;

        if (isNullOrEmpty(searchText))
            results = dao.getAll(pageNum, perPage);
        else
            results = dao.search(searchText, pageNum, perPage);

        return transform(results, new Function<U, T>() {
            @Override
            public T apply(U u) {
                return fromDatabaseObject(u);
            }
        });
    }

    @Override
    public T save(T item) {
        if (item == null)
            throw new ClientErrorException("Attempted to save null data");

        normalizeAndValidate(item);

        U savedObj = dao.save(toDatabaseObject(item));

        if (savedObj == null) {
            //TODO generate exception
        }

        return fromDatabaseObject(savedObj);
    }

    @Override
    public T saveOrUpdate(T item) {
        if (item == null)
            throw new ClientErrorException("Attempted to save null data");

        normalizeAndValidate(item);

        U savedObj = dao.saveOrUpdate(toDatabaseObject(item));

        if (savedObj == null) {
            //TODO generate exception
        }

        return fromDatabaseObject(savedObj);
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new NotFoundException();

        dao.delete(id);
    }
}
