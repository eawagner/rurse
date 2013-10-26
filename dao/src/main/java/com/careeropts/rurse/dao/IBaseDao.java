package com.careeropts.rurse.dao;


public interface IBaseDao<T> {

    public T getSingle(long id);

    public Iterable<T> getAll();

    public Iterable<T> getAll(int pageNum, int perPage);

    public Iterable<T> search(String searchText);

    public Iterable<T> search(String searchText, int pageNum, int perPage);

    public T save(T item);

    public T saveOrUpdate(T item);

    public boolean delete(long id);

}
