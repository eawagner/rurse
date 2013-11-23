package com.careeropts.rurse.dao.support;


import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;

import static org.apache.commons.logging.LogFactory.getLog;

/**
 * Utility class that is used to rebuild the Lucene indexes for the database.
 */
public class LuceneIndexRebuildAction {

    private static final Log logger = getLog(LuceneIndexRebuildAction.class);

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
