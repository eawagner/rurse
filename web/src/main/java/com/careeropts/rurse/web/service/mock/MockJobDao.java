package com.careeropts.rurse.web.service.mock;

import com.careeropts.rurse.dao.IJobDao;
import com.careeropts.rurse.dao.object.JobDO;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class MockJobDao implements IJobDao{

    @Override
    public JobDO getSingle(long id) {
        return new JobDO(id, "Title", "Description", null, null, null, true);
    }

    @Override
    public Iterable<JobDO> getAll() {
        return Arrays.asList(getSingle(1));
    }

    @Override
    public Iterable<JobDO> getAll(int pageNum, int perPage) {
        return getAll();
    }

    @Override
    public Iterable<JobDO> search(String searchText) {
        return getAll();
    }

    @Override
    public Iterable<JobDO> search(String searchText, int pageNum, int perPage) {
        return getAll();
    }

    @Override
    public JobDO save(JobDO item) {
        item.setId(1L);
        return item;
    }

    @Override
    public JobDO saveOrUpdate(JobDO item) {
        return item;
    }

    @Override
    public boolean delete(long id) {
        return true;
    }
}
