package com.careeropts.rurse.dao;

import com.careeropts.rurse.dao.object.UserDO;

public interface IUserDao extends IBaseDao<UserDO> {

    UserDO getByEmail(String email);

}
