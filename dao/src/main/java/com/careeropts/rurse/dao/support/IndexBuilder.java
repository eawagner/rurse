package com.careeropts.rurse.dao.support;

import com.careeropts.rurse.dao.object.BookEntity;
import com.careeropts.rurse.dao.object.CourseEntity;
import com.careeropts.rurse.dao.object.JobEntity;
import com.careeropts.rurse.dao.object.UserEntity;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.hibernate.search.Search.getFullTextSession;

/**
 * Utility class to rebuild the LuceneIndex from the database entities
 */
@Component
@Transactional
public class IndexBuilder {

    @Autowired
    private SessionFactory sessionFactory;

    private static <T> void indexEntity(FullTextSession session, Class<T> clazz) {

        session.purgeAll(clazz);

        ScrollableResults results = session.createCriteria(clazz).scroll();
        while (results.next())
            session.index(results.get(0));

    }

    public void build() throws InterruptedException {
        FullTextSession session = getFullTextSession(sessionFactory.getCurrentSession());
        indexEntity(session, UserEntity.class);
        indexEntity(session, BookEntity.class);
        indexEntity(session, CourseEntity.class);
        indexEntity(session, JobEntity.class);

//        getFullTextSession(sessionFactory.getCurrentSession())
//                .createIndexer()
//                .purgeAllOnStart(true)
//                .startAndWait();
    }

}
