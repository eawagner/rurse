package com.careeropts.rurse.dao.impl;


import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.dao.IUserDao;
import com.careeropts.rurse.dao.object.ResumeEntity;
import com.careeropts.rurse.dao.object.UserEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.*;

public class UserDaoTest extends AbstractBaseDaoTest<UserEntity>{

    @Autowired
    IUserDao dao;

    public static UserEntity genEntity(Long id, String resumeText) {
        UserEntity entity = new UserEntity(
                randomUUID().toString(),
                randomUUID().toString(),
                true,
                new ResumeEntity(
                        "resume.txt",
                        "text/plain",
                        resumeText.getBytes()
                )
        );

        entity.setId(id);
        return entity;
    }

    public static UserEntity copyEntity(UserEntity entity) {
        ResumeEntity resume = entity.getResume();
        if (resume != null) {
            Long id = resume.getId();
            resume = new ResumeEntity(
                    resume.getFileName(),
                    resume.getFileType(),
                    resume.getData()
            );
            resume.setId(id);
        }

        UserEntity user = new UserEntity(
                entity.getEmail(),
                entity.getPassword(),
                entity.isManager(),
                resume
        );
        user.setId(entity.getId());

        return user;
    }

    @Override
    protected IBaseDao<UserEntity> getDao() {
        return dao;
    }

    @Override
    protected UserEntity genTestEntity() {
        return genEntity(null, randomUUID().toString());
    }

    @Override
    protected UserEntity genUpdateEntity(UserEntity entity) {
        return genEntity(entity.getId(), randomUUID().toString());
    }

    @Override
    protected UserEntity copy(UserEntity entity) {
        return copyEntity(entity);
    }

    @Override
    protected Long getId(UserEntity entity) {
        return entity.getId();
    }


    @Test
    public void getByEmailTest() {
        UserEntity expected = copy(getDao().save(genTestEntity()));

        //Validate that the single element can be retrieved.
        UserEntity actual = dao.getByEmail(expected.getEmail());
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void getByEmailNullTest() {
        UserEntity actual = dao.getByEmail(null);
        assertNull(actual);
    }

    @Test
    public void deleteResumeTest() {
        UserEntity user = getDao().save(genTestEntity());
        ResumeEntity resume = user.getResume();

        user.setResume(null);
        //Unlink the resume from the user.
        dao.update(user);

        assertTrue(dao.deleteResume(resume));
    }

    @Test
    public void deleteResumeNullTest() {
        assertFalse(dao.deleteResume(null));
    }
}
