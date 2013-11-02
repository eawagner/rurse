package com.careeropts.rurse.web.service;

import java.util.List;

/**
 * Basic business layer interface to define common methods for basic business logic.
 * @param <T>
 */
public interface ISimpleService<T> {

    /**
     * Retrieves a single entity with the given id.
     * @param id The id of the entity.
     */
    public T getSingle(Long id);

    /**
     * Queries the system for all entities.  The query can be controlled via the use of search text and/or pagination
     * options.
     * @param searchText Search text to limit the results.
     * @param pageNum The page number of the results to be retrieved.
     * @param perPage The number of results per page.
     */
    public List<T> query(String searchText, Integer pageNum, Integer perPage);

    /**
     * Saves an new entity into the system.
     * @param item Entity to save.
     */
    public T save(T item);

    /**
     * Saves a new entity into the system or updates an existing entity.
     * @param item Entity to save.
     */
    public T update(T item);

    /**
     * Deletes an entity from the system with the given id.
     * @param id The id of the entity.
     */
    public void delete(Long id);
}
