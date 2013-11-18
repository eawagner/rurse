package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.dao.IBookDao;
import com.careeropts.rurse.dao.object.BookEntity;
import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.web.service.IBookService;
import com.careeropts.rurse.web.service.util.EntityTransform;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class BookServiceTest extends AbstractSimpleServiceTest<Book, BookEntity> {

    @Override
    protected IBookDao mockDao() {
        return mock(IBookDao.class);
    }

    @Override
    protected IBookService getService(IBaseDao<BookEntity> dao) {
        return new BookService((IBookDao) dao);
    }

    @Override
    protected Book genModel(Long id, String title, String description) {
        return new Book(id, title, description, null, null, null, null);
    }

    @Override
    protected BookEntity toEntity(Book model) {
        return EntityTransform.toEntity(model);
    }
}
