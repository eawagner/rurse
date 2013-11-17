package com.careeropts.rurse.client.impl;


import com.careeropts.rurse.client.IUserOperations;
import com.careeropts.rurse.client.exception.RurseAppException;
import com.careeropts.rurse.client.exception.UriException;
import com.careeropts.rurse.model.*;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.careeropts.rurse.client.util.Endpoints.*;
import static com.careeropts.rurse.client.util.Requests.*;
import static com.careeropts.rurse.client.util.ResponseHandlers.*;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Implementation for {@link IUserOperations} which uses a {@link HttpClient} to communicate with the
 * REST endpoints on the RURSE system.
 */
public class UserOperations implements IUserOperations {

    protected final String baseUrl;
    protected final HttpClient client;
    protected final HttpContext context;

    public UserOperations(String baseUrl, HttpClient client, HttpContext context) {
        this.baseUrl = baseUrl;
        this.client = client;
        this.context = context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> getBooks(int page, int pageSize) {
        return searchBooks("", page, pageSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> searchBooks(String keyWords, int page, int pageSize) {
        notNull(keyWords, "Unable to search.  Keywords are null");
        try {
            URI uri = builder(baseUrl, BOOK_ENDPOINT)
                    .addParameter("search", keyWords)
                    .addParameter("pageNum", Integer.toString(page))
                    .addParameter("resultSize", Integer.toString(pageSize))
                    .build();

            return client.execute(
                    get(uri, JSON),
                    jsonListResponse(BOOK_LIST_TYPE_REF),
                    context
            );
        } catch (URISyntaxException e) {
            throw new UriException(e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Book getBook(long id) {
        try {
            URI uri = builder(baseUrl, SINGLE_BOOK_ENDPOINT, id)
                    .build();

            return client.execute(
                    get(uri, JSON),
                    jsonResponse(Book.class),
                    context
            );
        } catch (URISyntaxException e) {
            throw new UriException(e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Course> getCourses(int page, int pageSize) {
        return searchCourses("", page, pageSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Course> searchCourses(String keyWords, int page, int pageSize) {
        notNull(keyWords, "Unable to search.  Keywords are null");
        try {
            URI uri = builder(baseUrl, COURSE_ENDPOINT)
                    .addParameter("search", keyWords)
                    .addParameter("pageNum", Integer.toString(page))
                    .addParameter("resultSize", Integer.toString(pageSize))
                    .build();

            return client.execute(
                    get(uri, JSON),
                    jsonListResponse(COURSE_LIST_TYPE_REF),
                    context
            );
        } catch (URISyntaxException e) {
            throw new UriException(e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Course getCourse(long id) {
        try {
            URI uri = builder(baseUrl, SINGLE_COURSE_ENDPOINT, id)
                    .build();

            return client.execute(
                    get(uri, JSON),
                    jsonResponse(Course.class),
                    context
            );
        } catch (URISyntaxException e) {
            throw new UriException(e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Job> getJobs(int page, int pageSize) {
        return searchJobs("", page, pageSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Job> searchJobs(String keyWords, int page, int pageSize) {
        notNull(keyWords, "Unable to search.  Keywords are null");
        try {
            URI uri = builder(baseUrl, JOB_ENDPOINT)
                    .addParameter("search", keyWords)
                    .addParameter("pageNum", Integer.toString(page))
                    .addParameter("resultSize", Integer.toString(pageSize))
                    .build();

            return client.execute(
                    get(uri, JSON),
                    jsonListResponse(JOB_LIST_TYPE_REF),
                    context
            );
        } catch (URISyntaxException e) {
            throw new UriException(e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Job getJob(long id) {
        try {
            URI uri = builder(baseUrl, SINGLE_JOB_ENDPOINT, id)
                    .build();

            return client.execute(
                    get(uri, JSON),
                    jsonResponse(Job.class),
                    context
            );
        } catch (URISyntaxException e) {
            throw new UriException(e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserInfo() {
        try {
            URI uri = builder(baseUrl, CURRENT_USER_ENDPOINT)
                    .build();
            return client.execute(
                    get(uri, JSON),
                    jsonResponse(User.class),
                    context
            );
        } catch (URISyntaxException e) {
            throw new UriException(e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getResume() {
        try {
            URI uri = builder(baseUrl, CURRENT_USER_RESUME_ENDPOINT)
                    .build();
            return client.execute(
                    getAny(uri),
                    streamResponse(),
                    context
            );
        } catch (URISyntaxException e) {
            throw new UriException(e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resume uploadResume(String fileName, InputStream resume) {
        notEmpty(fileName, "File name required for a resume");
        notNull(resume, "Resume data required for upload");
        try {
            //Strip path from filename.
            fileName = new File(fileName).getName();

            URI uri = builder(baseUrl, CURRENT_USER_RESUME_ENDPOINT)
                    .setParameter("fileName", fileName)
                    .build();
            return client.execute(
                    postStream(uri, JSON, resume),
                    jsonResponse(Resume.class),
                    context
            );
        } catch (URISyntaxException e) {
            throw new UriException(e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteResume() {
        try {
            URI uri = builder(baseUrl, CURRENT_USER_RESUME_ENDPOINT)
                    .build();
            client.execute(
                    delete(uri),
                    simpleResponse(),
                    context
            );
        } catch (URISyntaxException e) {
            throw new UriException(e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> getRecommendedBooks(int page, int pageSize) {
        try {
            URI uri = builder(baseUrl, RECOMMENDED_BOOK_ENDPOINT)
                    .addParameter("pageNum", Integer.toString(page))
                    .addParameter("resultSize", Integer.toString(pageSize))
                    .build();

            return client.execute(
                    get(uri, JSON),
                    jsonListResponse(BOOK_LIST_TYPE_REF),
                    context
            );
        } catch (URISyntaxException e) {
            throw new UriException(e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Course> getRecommendedCourses(int page, int pageSize) {
        try {
            URI uri = builder(baseUrl, RECOMMENDED_COURSE_ENDPOINT)
                    .addParameter("pageNum", Integer.toString(page))
                    .addParameter("resultSize", Integer.toString(pageSize))
                    .build();

            return client.execute(
                    get(uri, JSON),
                    jsonListResponse(COURSE_LIST_TYPE_REF),
                    context
            );
        } catch (URISyntaxException e) {
            throw new UriException(e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Job> getRecommendedJobs(int page, int pageSize) {
        try {
            URI uri = builder(baseUrl, RECOMMENDED_JOB_ENDPOINT)
                    .addParameter("pageNum", Integer.toString(page))
                    .addParameter("resultSize", Integer.toString(pageSize))
                    .build();

            return client.execute(
                    get(uri, JSON),
                    jsonListResponse(JOB_LIST_TYPE_REF),
                    context
            );
        } catch (URISyntaxException e) {
            throw new UriException(e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }
}
