package com.careeropts.rurse.dao.impl;

import com.careeropts.rurse.dao.IBaseDao;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.util.Version;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.hibernate.search.Search.getFullTextSession;

public abstract class AbstractBaseDao<T> implements IBaseDao<T> {

    @Autowired
    SessionFactory sessionFactory;

    protected abstract Class<T> getDOClass();
    protected abstract String[] getSearchFields();
    protected abstract String getAnalyzer();


    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getSingle(long id) {
        return (T) getSession().get(getDOClass(), id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll() {
        return getAll(0, Integer.MAX_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll(int pageNum, int perPage) {
        return getSession()
                .createCriteria(getDOClass())
                .setFirstResult(pageNum * perPage)
                .setMaxResults(perPage)
                .list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> search(String searchText) {
        return search(searchText, 0, Integer.MAX_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> search(String searchText, int pageNum, int perPage) {
        if (searchText == null)
            return emptyList();

        FullTextSession fullTextSession = getFullTextSession(getSession());

        MultiFieldQueryParser parser = new MultiFieldQueryParser(
                Version.LUCENE_36,
                getSearchFields(),
                fullTextSession.getSearchFactory().getAnalyzer(getAnalyzer())
        );

        try {
            return fullTextSession
                    .createFullTextQuery(parser.parse(searchText), getDOClass())
                    .setFirstResult(pageNum * perPage)
                    .setMaxResults(perPage)
                    .list();

        } catch (ParseException e) {
            //TODO log
            return emptyList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(T item) {
        if (item == null)
            return null;

        getSession().save(item);
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(T item) {
        if (item ==null)
            return null;

        getSession().merge(item);
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(T item) {
        if (item == null)
            return false;

        getSession().delete(item);
        return true;
    }
}
