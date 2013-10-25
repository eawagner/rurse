package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.object.BookDO;
import com.careeropts.rurse.model.Book;

public class BookService extends AbstractSimpleService<Book, BookDO> {

    @Override
    protected void normalizeAndValidate(Book model) {
        //TODO
    }

    @Override
    protected BookDO toDatabaseObject(Book model) {
        if (model == null)
            return null;

        return new BookDO(
                model.getId(),
                model.getTitle(),
                model.getDescription(),
                model.getPublisher(),
                model.getPublishDate(),
                model.getPrice(),
                model.getISBN()
        );
    }

    @Override
    protected Book fromDatabaseObject(BookDO dataObject) {
        if (dataObject == null)
            return null;

        return new Book(
                dataObject.getId(),
                dataObject.getTitle(),
                dataObject.getDescription(),
                dataObject.getPublisher(),
                dataObject.getPublishDate(),
                dataObject.getPrice(),
                dataObject.getISBN()
        );
    }
}
