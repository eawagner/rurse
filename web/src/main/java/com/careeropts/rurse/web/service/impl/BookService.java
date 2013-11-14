package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.IBookDao;
import com.careeropts.rurse.dao.object.BookEntity;
import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.web.exception.BadRequestException;
import com.careeropts.rurse.web.service.IBookService;
import com.careeropts.rurse.web.service.util.EntityTransform;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.Math.round;

@Service
public class BookService extends AbstractSimpleService<Book, BookEntity> implements IBookService {

    @Autowired
    public BookService(IBookDao dao) {
        super(dao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long getId(Book model) {
        return model.getId();
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected BookEntity toEntity(Book model) {
        return EntityTransform.toEntity(model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Book fromEntity(BookEntity entity) {
        return EntityTransform.fromEntity(entity);
    }
}
