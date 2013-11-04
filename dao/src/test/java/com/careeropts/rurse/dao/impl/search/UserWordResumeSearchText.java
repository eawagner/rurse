package com.careeropts.rurse.dao.impl.search;


import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.dao.IUserDao;
import com.careeropts.rurse.dao.object.UserEntity;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.careeropts.rurse.dao.impl.UserDaoTest.copyEntity;
import static com.careeropts.rurse.dao.impl.UserDaoTest.genEntity;

public class UserWordResumeSearchText extends AbstractSearchTest<UserEntity> {

    @Autowired
    IUserDao dao;

    @Override
    protected IBaseDao<UserEntity> getDao() {
        return dao;
    }

    @Override
    protected UserEntity genTestEntity(String searchText) {
        XWPFDocument doc = new XWPFDocument();
        doc.createParagraph().createRun().setText(searchText);

        byte[] data;

        ByteArrayOutputStream output = new ByteArrayOutputStream(searchText.length());
        try {
            doc.write(output);
            output.close();
            data = output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return genEntity(null, "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", data);
    }

    @Override
    protected UserEntity copy(UserEntity entity) {
        return copyEntity(entity);
    }
}
