package com.careeropts.rurse.dao.impl;

import com.careeropts.rurse.dao.IBaseDao;

public abstract class AbstractBaseDao<T> implements IBaseDao<T> {

    @Override
    public T getSingle(long id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<T> getAll() {
        return getAll(0, Integer.MAX_VALUE);
    }

    @Override
    public Iterable<T> getAll(int pageNum, int perPage) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<T> search(String searchText) {
        return search(null, 0, Integer.MAX_VALUE);
    }

    @Override
    public Iterable<T> search(String searchText, int pageNum, int perPage) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public T save(T item) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public T saveOrUpdate(T item) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(long id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
