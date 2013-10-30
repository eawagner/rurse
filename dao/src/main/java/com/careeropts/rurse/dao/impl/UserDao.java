package com.careeropts.rurse.dao.impl;

import com.careeropts.rurse.dao.IUserDao;
import com.careeropts.rurse.dao.object.ResumeDO;
import com.careeropts.rurse.dao.object.UserDO;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class UserDao extends AbstractBaseDao<UserDO> implements IUserDao {
    @Override
    protected Class<UserDO> getDOClass() {
        return UserDO.class;
    }

    @Override
    public UserDO getByEmail(String email) {
        return (UserDO) getSession()
                .createCriteria(getDOClass())
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public boolean deleteResume(long resumeId) {
        ResumeDO resume = (ResumeDO)getSession().get(ResumeDO.class, resumeId);

        if (resume == null)
            return false;

        getSession().delete(resume);

        return true;
    }
}
