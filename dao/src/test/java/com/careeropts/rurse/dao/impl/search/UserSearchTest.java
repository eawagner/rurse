package com.careeropts.rurse.dao.impl.search;

import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.dao.IUserDao;
import com.careeropts.rurse.dao.object.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import static com.careeropts.rurse.dao.impl.UserDaoTest.copyEntity;
import static com.careeropts.rurse.dao.impl.UserDaoTest.genEntity;

public class UserSearchTest extends AbstractSearchTest<UserEntity> {

    @Autowired
    IUserDao dao;

    @Override
    protected IBaseDao<UserEntity> getDao() {
        return dao;
    }

    @Override
    protected UserEntity genTestEntity(String searchText) {
        return genEntity(null, searchText);
    }

    @Override
    protected UserEntity copy(UserEntity entity) {
        return copyEntity(entity);
    }
}
