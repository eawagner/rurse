package com.careeropts.rurse.dao.impl;

import com.careeropts.rurse.dao.IBaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractBaseDao<T> implements IBaseDao<T> {

    @Autowired
    SessionFactory sessionFactory;

    protected abstract Class<T> getDOClass();

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public T getSingle(long id) {
        return (T) getSession().get(getDOClass(), id);
    }

    @Override
    public Iterable<T> getAll() {
        return getAll(0, Integer.MAX_VALUE);
    }

    @Override
    public Iterable<T> getAll(int pageNum, int perPage) {
        return getSession()
                .createCriteria(getDOClass())
                .setFirstResult(pageNum * perPage)
                .setMaxResults(perPage)
                .list();
    }

    @Override
    public Iterable<T> search(String searchText) {
        return search(null, 0, Integer.MAX_VALUE);
    }

    @Override
    public Iterable<T> search(String searchText, int pageNum, int perPage) {
        return null;
    }

    @Override
    public T save(T item) {
        getSession().save(item);
        return item;
    }

    @Override
    public T saveOrUpdate(T item) {
        getSession().saveOrUpdate(item);
        return item;
    }

    @Override
    public boolean delete(long id) {
        T item = getSingle(id);

        if (item == null)
            return false;

        getSession().delete(item);

        return true;
    }
}
