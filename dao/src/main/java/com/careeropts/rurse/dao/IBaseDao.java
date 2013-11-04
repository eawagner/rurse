package com.careeropts.rurse.dao;


import java.util.List;

public interface IBaseDao<T> {

    /**
     * Retrieves an entity with the given id.
     */
    public T getSingle(long id);

    /**
     * Retrieves all entities managed by the dao with pagination parameters.
     */
    public List<T> getAll(int pageNum, int perPage);

    /**
     * Retrieves all entities managed by the dao matches the given search text with pagination parameters.
     */
    public List<T> search(String searchText, int pageNum, int perPage);

    /**
     * Saves a new entity to the dao
     */
    public T save(T item);

    /**
     * Updates the entity in the dao.
     */
    public T update(T item);

    /**
     * Deletes the entity with the given id from the dao.
     */
    public boolean delete(T item);

}
