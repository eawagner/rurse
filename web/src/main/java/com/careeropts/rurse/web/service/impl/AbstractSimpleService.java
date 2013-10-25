package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.IBaseDao;
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
    public T getSingle(String id) {
        if (id == null) {
            //TODO generate 404 exception
        }

        U item = dao.getSingle(id);

        if (item == null) {
            //TODO generate 404 exception
        }

        return fromDatabaseObject(item);
    }

    @Override
    public Iterable<T> query(String searchText, Integer pageNum, Integer perPage) {
        if (pageNum == null || pageNum < 0)
            pageNum = 0;

        if (perPage == null || perPage < 0)
            perPage = Integer.MAX_VALUE;

        Iterable<U> results;
        if (isNullOrEmpty(searchText)) {
            results = dao.getAll(pageNum, perPage);
        } else {
            results = dao.search(searchText, pageNum, perPage);
        }

        return transform(results, new Function<U, T>() {
            @Override
            public T apply(U u) {
                return fromDatabaseObject(u);
            }
        });
    }

    @Override
    public T save(T item) {
        if (item == null) {
            //TODO generate 400 exception
        }

        normalizeAndValidate(item);

        U savedObj = dao.save(toDatabaseObject(item));

        if (savedObj == null) {
            //TODO generate exception
        }

        return fromDatabaseObject(savedObj);
    }

    @Override
    public T saveOrUpdate(T item) {
        if (item == null) {
            //TODO generate 400 exception
        }

        normalizeAndValidate(item);

        U savedObj = dao.saveOrUpdate(toDatabaseObject(item));

        if (savedObj == null) {
            //TODO generate exception
        }

        return fromDatabaseObject(savedObj);
    }

    @Override
    public void delete(String id) {
        if (id == null) {
            //TODO generate 404 exception
        }

        dao.delete(id);
    }
}
