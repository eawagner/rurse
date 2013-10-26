package com.careeropts.rurse.web.service.mock;

import com.careeropts.rurse.dao.ICourseDao;
import com.careeropts.rurse.dao.object.CourseDO;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class MockCourseDao implements ICourseDao{

    @Override
    public CourseDO getSingle(long id) {
        return new CourseDO(id, "Title", "Description", null, null);
    }

    @Override
    public Iterable<CourseDO> getAll() {
        return Arrays.asList(getSingle(1));
    }

    @Override
    public Iterable<CourseDO> getAll(int pageNum, int perPage) {
        return getAll();
    }

    @Override
    public Iterable<CourseDO> search(String searchText) {
        return getAll();
    }

    @Override
    public Iterable<CourseDO> search(String searchText, int pageNum, int perPage) {
        return getAll();
    }

    @Override
    public CourseDO save(CourseDO item) {
        item.setId(1L);
        return item;
    }

    @Override
    public CourseDO saveOrUpdate(CourseDO item) {
        return item;
    }

    @Override
    public boolean delete(long id) {
        return true;
    }
}
