package com.careeropts.rurse.web.service;

public interface ISimpleService<T> {

    public T getSingle(String id);

    public Iterable<T> query(String searchText, Integer pageNum, Integer perPage);

    public T save(T item);

    public T saveOrUpdate(T item);

    public void delete(String id);
}
