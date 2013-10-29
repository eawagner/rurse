package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.IBookDao;
import com.careeropts.rurse.dao.object.BookDO;
import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.web.exception.BadRequestException;
import com.careeropts.rurse.web.service.IBookService;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.Integer.valueOf;
import static java.lang.Math.round;
import static org.apache.commons.lang3.math.NumberUtils.isDigits;

@Service
public class BookService extends AbstractSimpleService<Book, BookDO> implements IBookService {

    @Autowired
    public BookService(IBookDao dao) {
        super(dao);
    }

    @Override
    protected void normalizeAndValidate(Book model) {

        if (isNullOrEmpty(model.getTitle()))
            throw new BadRequestException("A book must have a title");


        if (isNullOrEmpty(model.getDescription()))
            throw new BadRequestException("A book must have a description");

        //Only try to validate the ISBN if it is actually set
        if (model.getIsbn() != null && !ISBNValidator.getInstance().isValid(model.getIsbn()))
            throw new BadRequestException("The ISBN must follow the 10 or 13 digit ISBN standard");


        //normalize price to a two digit value
        if (model.getPrice() != null)
            model.setPrice(round(model.getPrice() * 100.0) / 100.0);

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
                model.getIsbn()
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
                dataObject.getIsbn()
        );
    }
}
