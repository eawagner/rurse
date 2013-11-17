package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.web.exception.BadRequestException;
import com.careeropts.rurse.web.exception.InternalServerError;
import com.careeropts.rurse.web.exception.NotFoundException;
import com.careeropts.rurse.web.service.ISimpleService;
import com.google.common.base.Function;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.transform;


@Transactional
public abstract class AbstractSimpleService<T, U> implements ISimpleService<T> {

    protected final IBaseDao<U> dao;

    protected AbstractSimpleService(IBaseDao<U> dao) {
        this.dao = dao;
    }

    /**
     * Converts the model to a database entity.
     * @param model Model to convert.
     */
    protected abstract U toEntity(T model);

    /**
     * Converts a database entity to a model.
     * @param dataObject Database object to convert.
     */
    protected abstract T fromEntity(U dataObject);

    /**
     * Validate and normalizes data in the provided model.
     * @param model Model to check.
     */
    protected abstract void normalizeAndValidate(T model);

    /**
     * Retrieves the id from a model object.
     * @param model Model to retrieve the id from.
     */
    protected abstract Long getId(T model);

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public T getSingle(Long id) {
        if (id == null)
            throw new NotFoundException();

        U entity = dao.getSingle(id);

        if (entity == null)
            throw new NotFoundException();


        return fromEntity(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<T> query(String searchText, Integer pageNum, Integer perPage) {
        if (pageNum == null || pageNum < 0)
            pageNum = 0;

        if (perPage == null || perPage < 0)
            perPage = 50;

        List<U> results;

        if (isNullOrEmpty(searchText))
            results = dao.getAll(pageNum, perPage);
        else
            results = dao.search(searchText, pageNum, perPage);

        return transform(results, new Function<U, T>() {
            @Override
            public T apply(U u) {
                return fromEntity(u);
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

        U entity = dao.save(toEntity(item));

        if (entity == null)
            throw new InternalServerError();

        return fromEntity(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(T item) {
        if (item == null)
            throw new BadRequestException("Attempted to save null data");

        //Call get single to generate NotFoundExceptions if the object being saved is not already there.
        getSingle(getId(item));

        normalizeAndValidate(item);
        U entity = dao.update(toEntity(item));

        if (entity == null) {
            throw new InternalServerError();
        }

        return fromEntity(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        if (id == null)
            throw new NotFoundException();

        if (!dao.delete(dao.getSingle(id)))
            throw new NotFoundException();

    }
}
