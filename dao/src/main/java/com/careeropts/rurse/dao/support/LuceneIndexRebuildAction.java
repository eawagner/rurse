package com.careeropts.rurse.dao.support;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Utility class that is used to rebuild the Lucene indexes for the database.
 */
public class LuceneIndexRebuildAction {

    private static final Logger logger = LoggerFactory.getLogger(LuceneIndexRebuildAction.class);

    private boolean rebuild = false;

    private final IndexBuilder indexBuilder;

    @Autowired
    public LuceneIndexRebuildAction(IndexBuilder indexBuilder) {
        this.indexBuilder = indexBuilder;
    }

    public void setRebuild(boolean rebuild) {
        this.rebuild = rebuild;
    }

    public void start() throws InterruptedException {
        if (rebuild) {

            logger.info("Start of rebuilding of the Lucene indexes");

            indexBuilder.build();

            logger.info("Index rebuild completed");

        } else {
            logger.info("Skipping Lucene index rebuild.");
        }

    }
}
