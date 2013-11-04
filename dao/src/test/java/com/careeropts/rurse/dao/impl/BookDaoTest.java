package com.careeropts.rurse.dao.impl;

import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.dao.IBookDao;
import com.careeropts.rurse.dao.object.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Random;

import static java.util.UUID.randomUUID;

public class BookDaoTest extends AbstractBaseDaoTest<BookEntity> {

    @Autowired
    IBookDao dao;

    private static Random random = new Random();

    public static BookEntity genEntity(Long id, String title) {
        return new BookEntity(
                id,
                title,
                randomUUID().toString(),
                randomUUID().toString(),
                new Date(random.nextLong()),
                random.nextDouble(),
                randomUUID().toString()
        );
    }

    public static BookEntity copyEntity(BookEntity entity) {
        return new BookEntity(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPublisher(),
                entity.getPublishDate(),
                entity.getPrice(),
                entity.getIsbn()
        );
    }

    @Override
    protected IBaseDao<BookEntity> getDao() {
        return dao;
    }

    @Override
    protected BookEntity genTestEntity() {
        return genEntity(null, randomUUID().toString());
    }

    @Override
    protected BookEntity genUpdateEntity(BookEntity entity) {
        return genEntity(entity.getId(), entity.getTitle());
    }

    @Override
    protected BookEntity copy(BookEntity entity) {
        return copyEntity(entity);
    }

    @Override
    protected Long getId(BookEntity entity) {
        return entity.getId();
    }
}
