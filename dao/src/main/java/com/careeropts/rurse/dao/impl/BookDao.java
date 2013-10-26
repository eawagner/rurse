package com.careeropts.rurse.dao.impl;


import com.careeropts.rurse.dao.object.BookDO;

public class BookDao extends AbstractBaseDao<BookDO>{

    @Override
    protected Class<BookDO> getDOClass() {
        return BookDO.class;
    }
}
