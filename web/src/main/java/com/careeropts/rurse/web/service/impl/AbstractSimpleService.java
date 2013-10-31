package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.web.exception.BadRequestException;
import com.careeropts.rurse.web.exception.InternalServerError;
import com.careeropts.rurse.web.exception.NotFoundException;
import com.careeropts.rurse.web.service.ISimpleService;
import com.google.common.base.Function;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Iterables.transform;


@Transactional
public abstract class AbstractSimpleService<T, U> implements ISimpleService<T> {

    IBaseDao<U> dao;

    protected AbstractSimpleService(IBaseDao<U> dao) {
        this.dao = dao;
    }

    /**
     * Converts the entity model to a database object.
     * @param model Model to convert.
     */
    protected abstract U toDatabaseObject (T model);

    /**
     * Converts a database object to an entity model.
     * @param dataObject Database object to convert.
     */
    protected abstract T fromDatabaseObject(U dataObject);

    /**
     * Validate and normalizes data in the provided model.
     * @param model Model to check.
     */
    protected abstract void normalizeAndValidate(T model);

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public T getSingle(Long id) {
        if (id == null)
            throw new NotFoundException();

        U item = dao.getSingle(id);

        if (item == null)
            throw new NotFoundException();


        return fromDatabaseObject(item);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
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

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(T item) {
        if (item == null)
            throw new BadRequestException("Attempted to save null data");

        normalizeAndValidate(item);

        U savedObj = dao.save(toDatabaseObject(item));

        if (savedObj == null) {
            throw new InternalServerError();
        }

        return fromDatabaseObject(savedObj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T saveOrUpdate(T item) {
        if (item == null)
            throw new BadRequestException("Attempted to save null data");

        normalizeAndValidate(item);

        U savedObj = dao.saveOrUpdate(toDatabaseObject(item));

        if (savedObj == null) {
            throw new InternalServerError();
        }

        return fromDatabaseObject(savedObj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        if (id == null)
            throw new NotFoundException();

        if (!dao.delete(id))
            throw new NotFoundException();

    }
}
