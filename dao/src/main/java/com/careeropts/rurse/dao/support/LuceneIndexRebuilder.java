package com.careeropts.rurse.dao.support;


import com.careeropts.rurse.dao.object.BookEntity;
import com.careeropts.rurse.dao.object.CourseEntity;
import com.careeropts.rurse.dao.object.JobEntity;
import com.careeropts.rurse.dao.object.UserEntity;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.hibernate.search.Search.getFullTextSession;

/**
 * Utility class that is used to rebuild the Lucene indexes for the database.
 */
public class LuceneIndexRebuilder {

    private static Logger logger = LoggerFactory.getLogger(LuceneIndexRebuilder.class);

    private boolean rebuild = false;

    @Autowired
    SessionFactory sessionFactory;

    public void setRebuild(boolean rebuild) {
        this.rebuild = rebuild;
    }

    public void start() throws InterruptedException {
        if (rebuild) {

            logger.info("Start of rebuilding of the Lucene indexes");

            FullTextSession session = getFullTextSession(sessionFactory.openSession());

            session.createIndexer()
                    .startAndWait();

            logger.info("Index rebuild completed");

            session.close();

        } else {
            logger.info("Skipping Lucene index rebuild.");
        }

    }
}
