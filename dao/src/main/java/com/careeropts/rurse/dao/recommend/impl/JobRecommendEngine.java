package com.careeropts.rurse.dao.recommend.impl;


import com.careeropts.rurse.dao.IUserDao;
import com.careeropts.rurse.dao.object.JobEntity;
import com.careeropts.rurse.dao.object.UserEntity;
import com.careeropts.rurse.dao.recommend.IJobRecommendEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * Simple recommendation Engine that simply converts the job title and description to single words and runs the resume words through
 * the keyword search algorithm to generate matches.
 */
@Component
public class JobRecommendEngine implements IJobRecommendEngine {

    private final IUserDao userDao;

    @Autowired
    public JobRecommendEngine(IUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Takes in a job and returns a string with all non alphanumeric characters converted to whitespace so only
     * words and letters are left as tokens for searching.
     * @param job Job Entity to tokenize
     */
    private static String tokenizeJob(JobEntity job) {
        if (job == null)
            return null;

        String data = new StringBuilder()
                .append(job.getTitle())
                .append(" ")
                .append(job.getDescription())
                .toString();

        return data.replaceAll("[^\\p{L}]", " ");  //replace all non-letters with whitespace
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserEntity> recommendUsers(JobEntity job, int pageNum, int perPage) {
        String tokens = tokenizeJob(job);
        if (tokens == null)
            return emptyList();

        return userDao.search(tokens, pageNum, perPage);
    }
}
