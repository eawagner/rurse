package com.careeropts.rurse.dao.recommend.impl;


import com.careeropts.rurse.dao.IBookDao;
import com.careeropts.rurse.dao.ICourseDao;
import com.careeropts.rurse.dao.IJobDao;
import com.careeropts.rurse.dao.object.BookEntity;
import com.careeropts.rurse.dao.object.CourseEntity;
import com.careeropts.rurse.dao.object.JobEntity;
import com.careeropts.rurse.dao.object.ResumeEntity;
import com.careeropts.rurse.dao.recommend.IUserRecommendEngine;
import org.apache.commons.logging.Log;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import static com.careeropts.rurse.dao.support.ResumeParser.parse;
import static java.util.Collections.emptyList;
import static org.apache.commons.logging.LogFactory.getLog;

/**
 * Simple recommendation Engine that simply converts the resume to single words and runs the resume words through
 * the keyword search algorithm to generate matches.
 */
@Component
public class UserRecommendEngine implements IUserRecommendEngine {

    private static final Log logger = getLog(UserRecommendEngine.class);

    private final IBookDao bookDao;

    private final ICourseDao courseDao;

    private final IJobDao jobDao;

    @Autowired
    public UserRecommendEngine(IBookDao bookDao, ICourseDao courseDao, IJobDao jobDao) {
        this.bookDao = bookDao;
        this.courseDao = courseDao;
        this.jobDao = jobDao;
    }

    /**
     * Takes in a resume and returns a string with all non alphanumeric characters converted to whitespace so only
     * words and letters are left as tokens for searching.
     * @param resume Resume Entity to parse
     */
    private static String tokenizeResume(ResumeEntity resume) {
        try {
            String data = parse(resume);
            if (data != null)
                return data.replaceAll("[^\\p{L}]", " ");  //replace all non-letters with whitespace

            return null;

        } catch (TikaException | SAXException | IOException e) {
            //Don't bomb out, just log it and let caller handle null like there was no data present for resume.
            logger.error("Error parsing resume document", e);
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookEntity> recommendBooks(ResumeEntity resume, int pageNum, int perPage) {
        String tokens = tokenizeResume(resume);
        if (tokens == null)
            return emptyList();

        return bookDao.search(tokens, pageNum, perPage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CourseEntity> recommendCourses(ResumeEntity resume, int pageNum, int perPage) {
        String tokens = tokenizeResume(resume);
        if (tokens == null)
            return emptyList();

        return courseDao.search(tokens, pageNum, perPage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JobEntity> recommendJobs(ResumeEntity resume, int pageNum, int perPage) {
        String tokens = tokenizeResume(resume);
        if (tokens == null)
            return emptyList();

        return jobDao.search(tokens, pageNum, perPage);
    }
}
