package com.careeropts.rurse.dao.impl;


import com.careeropts.rurse.dao.IBookDao;
import com.careeropts.rurse.dao.object.BookDO;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao extends AbstractBaseDao<BookDO> implements IBookDao {

    @Override
    protected Class<BookDO> getDOClass() {
        return BookDO.class;
    }
}
