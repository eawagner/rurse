package com.careeropts.rurse.dao;

import com.careeropts.rurse.dao.object.ResumeDO;
import com.careeropts.rurse.dao.object.UserDO;

/**
 * Data access layer for storage and the retrieval of users and resumes
 */
public interface IUserDao extends IBaseDao<UserDO> {

    /**
     * Retrieves the user with the given email address.
     * @param email Email address of the user to retrieve.
     */
    UserDO getByEmail(String email);

    /**
     * Deletes the given resume from the system.  This is a workaround to an issue where resumes can be orphaned.
     * @param resume Resume to delete.
     */
    boolean deleteResume(ResumeDO resume);

}
