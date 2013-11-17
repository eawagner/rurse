package com.careeropts.rurse.client.impl;


import com.careeropts.rurse.client.IManagerOperations;
import com.careeropts.rurse.client.exception.RurseAppException;
import com.careeropts.rurse.client.exception.UriException;
import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.model.User;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.careeropts.rurse.client.util.Endpoints.*;
import static com.careeropts.rurse.client.util.Requests.*;
import static com.careeropts.rurse.client.util.ResponseHandlers.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Implementation for {@link IManagerOperations} which uses a {@link HttpClient} to communicate with the
 * REST endpoints on the RURSE system.
 */
public class MangerOperations extends UserOperations implements IManagerOperations {

    public MangerOperations(String baseUrl, HttpClient client, HttpContext context) {
        super(baseUrl, client, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Book saveBook(Book book) {
        notNull(book, "No data to save");
        try {
            URI uri = builder(baseUrl, BOOK_ENDPOINT)
                    .build();
            return client.execute(
                    postJson(uri, JSON, book),
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
    public Book updateBook(Book book) {
        notNull(book, "No data to save");
        notNull(book.getId(), "No id found on the data");
        try {
            URI uri = builder(baseUrl, SINGLE_BOOK_ENDPOINT, book.getId())
                    .build();
            return client.execute(
                    postJson(uri, JSON, book),
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
    public void deleteBook(long id) {
        try {
            URI uri = builder(baseUrl, SINGLE_BOOK_ENDPOINT, id)
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
    public Course saveCourse(Course course) {
        notNull(course, "No data to save");
        try {
            URI uri = builder(baseUrl, COURSE_ENDPOINT)
                    .build();
            return client.execute(
                    postJson(uri, JSON, course),
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
    public Course updateCourse(Course course) {
        notNull(course, "No data to save");
        notNull(course.getId(), "No id found on the data");
        try {
            URI uri = builder(baseUrl, SINGLE_COURSE_ENDPOINT, course.getId())
                    .build();
            return client.execute(
                    postJson(uri, JSON, course),
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
    public void deleteCourse(long id) {
        try {
            URI uri = builder(baseUrl, SINGLE_COURSE_ENDPOINT, id)
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
    public Job saveJob(Job job) {
        notNull(job, "No data to save");
        try {
            URI uri = builder(baseUrl, JOB_ENDPOINT)
                    .build();
            return client.execute(
                    postJson(uri, JSON, job),
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
    public Job updateJob(Job job) {
        notNull(job, "No data to save");
        notNull(job.getId(), "No id found on the data");
        try {
            URI uri = builder(baseUrl, SINGLE_JOB_ENDPOINT, job.getId())
                    .build();
            return client.execute(
                    postJson(uri, JSON, job),
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
    public void deleteJob(long id) {
        try {
            URI uri = builder(baseUrl, SINGLE_JOB_ENDPOINT, id)
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
    public List<User> recommendedUsersForJob(long jobId, int page, int pageSize) {
        try {
            URI uri = builder(baseUrl, SINGLE_JOB_RECOMMENDED_USER_ENDPOINT, jobId)
                    .addParameter("pageNum", Integer.toString(page))
                    .addParameter("resultSize", Integer.toString(pageSize))
                    .build();
            return client.execute(
                    get(uri, JSON),
                    jsonListResponse(USER_LIST_TYPE_REF),
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
    public List<User> getUsers(int page, int pageSize) {
        return searchUsers("", page, pageSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> searchUsers(String keyWords, int page, int pageSize) {
        notNull(keyWords, "Unable to search.  Keywords are null");
        try {
            URI uri = builder(baseUrl, USER_ENDPOINT)
                    .addParameter("search", keyWords)
                    .addParameter("pageNum", Integer.toString(page))
                    .addParameter("resultSize", Integer.toString(pageSize))
                    .build();

            return client.execute(
                    get(uri, JSON),
                    jsonListResponse(USER_LIST_TYPE_REF),
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
    public User getUserInfo(long id) {
        try {
            URI uri = builder(baseUrl, SINGLE_USER_ENDPOINT, id)
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
    public InputStream getResume(long id) {
        try {
            URI uri = builder(baseUrl, SINGLE_USER_RESUME_ENDPOINT, id)
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
    public User changeAuthorization(long id, boolean makeManager) {
        try {
            URI uri = builder(baseUrl, SINGLE_USER_AUTH_ENDPOINT, id)
                    .setParameter("manager", (makeManager ? TRUE.toString() : FALSE.toString()))
                    .build();
            return client.execute(
                    post(uri, JSON),
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
    public void deleteUser(long id) {

        try {
            URI uri = builder(baseUrl, SINGLE_USER_ENDPOINT, id)
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
}
