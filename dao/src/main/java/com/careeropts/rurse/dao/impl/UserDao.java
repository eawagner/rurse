package com.careeropts.rurse.dao.impl;

import com.careeropts.rurse.dao.IUserDao;
import com.careeropts.rurse.dao.object.ResumeEntity;
import com.careeropts.rurse.dao.object.UserEntity;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class UserDao extends AbstractBaseDao<UserEntity> implements IUserDao {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<UserEntity> getDOClass() {
        return UserEntity.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity getByEmail(String email) {
        return (UserEntity) getSession()
                .createCriteria(getDOClass())
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteResume(ResumeEntity resume) {
        if (resume == null)
            return false;

        getSession().delete(resume);

        return true;
    }
}
