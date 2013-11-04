package com.careeropts.rurse.dao.impl.search;

import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.dao.IBookDao;
import com.careeropts.rurse.dao.object.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;

import static com.careeropts.rurse.dao.impl.BookDaoTest.copyEntity;
import static com.careeropts.rurse.dao.impl.BookDaoTest.genEntity;

public class BookSearchTest extends AbstractSearchTest<BookEntity> {

    @Autowired
    IBookDao dao;

    @Override
    protected IBaseDao<BookEntity> getDao() {
        return dao;
    }

    @Override
    protected BookEntity genTestEntity(String searchText) {
        return genEntity(null, searchText);
    }

    @Override
    protected BookEntity copy(BookEntity entity) {
        return copyEntity(entity);
    }
}
