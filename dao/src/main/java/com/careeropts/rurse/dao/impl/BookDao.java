package com.careeropts.rurse.dao.impl;


import com.careeropts.rurse.dao.IBookDao;
import com.careeropts.rurse.dao.object.BookEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao extends AbstractBaseDao<BookEntity> implements IBookDao {

    @Autowired
    public BookDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<BookEntity> getDOClass() {
        return BookEntity.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getSearchFields() {
        return new String[]{"title", "description"};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAnalyzer() {
        return "bookAnalyzer";
    }
}
