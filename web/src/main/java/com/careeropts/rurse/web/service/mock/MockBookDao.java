package com.careeropts.rurse.web.service.mock;

import com.careeropts.rurse.dao.IBookDao;
import com.careeropts.rurse.dao.object.BookDO;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class MockBookDao implements IBookDao{

    @Override
    public BookDO getSingle(long id) {
        return new BookDO(id, "Title", "Description", null, null, null, null);
    }

    @Override
    public Iterable<BookDO> getAll() {
        return Arrays.asList(getSingle(1));
    }

    @Override
    public Iterable<BookDO> getAll(int pageNum, int perPage) {
        return getAll();
    }

    @Override
    public Iterable<BookDO> search(String searchText) {
        return getAll();
    }

    @Override
    public Iterable<BookDO> search(String searchText, int pageNum, int perPage) {
        return getAll();
    }

    @Override
    public BookDO save(BookDO item) {
        item.setId(1L);
        return item;
    }

    @Override
    public BookDO saveOrUpdate(BookDO item) {
        return item;
    }

    @Override
    public boolean delete(long id) {
        return true;
    }
}
