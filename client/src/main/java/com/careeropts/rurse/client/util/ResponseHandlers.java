package com.careeropts.rurse.client.util;


import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.careeropts.rurse.client.exception.ExceptionMapper.responseException;
import static org.apache.commons.io.IOUtils.toByteArray;
import static org.apache.http.HttpStatus.SC_OK;

public class ResponseHandlers {
    private ResponseHandlers() {/*private constructor*/}

    private static ObjectMapper mapper = new ObjectMapper();

    public static TypeReference<List<Book>> BOOK_LIST_TYPE_REF = new TypeReference<List<Book>>() {};
    public static TypeReference<List<Course>> COURSE_LIST_TYPE_REF = new TypeReference<List<Course>>() {};
    public static TypeReference<List<Job>> JOB_LIST_TYPE_REF = new TypeReference<List<Job>>() {};
    public static TypeReference<List<User>> USER_LIST_TYPE_REF = new TypeReference<List<User>>() {};

    public static <T> ResponseHandler<T> simpleResponse() {
        return new ResponseHandler<T>() {
            @Override
            public T handleResponse(HttpResponse response) throws IOException {
                if (response.getStatusLine().getStatusCode() == SC_OK)
                    return null;
                else
                    responseException(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));

                return null;
            }
        };
    }

    public static ResponseHandler<InputStream> streamResponse() {
        return new ResponseHandler<InputStream>() {
            @Override
            public InputStream handleResponse(HttpResponse response) throws IOException {
                if (response.getStatusLine().getStatusCode() == SC_OK)
                    try (InputStream input = response.getEntity().getContent()) {
                        return new ByteArrayInputStream(toByteArray(input));
                    }
                else
                    throw responseException(
                            response.getStatusLine().getStatusCode(),
                            EntityUtils.toString(response.getEntity())
                    );
            }
        };
    }

    public static <T> ResponseHandler<T> jsonResponse(final Class<T> clazz) {
        return new ResponseHandler<T>() {
            @Override
            public T handleResponse(HttpResponse response) throws IOException {
                if (response.getStatusLine().getStatusCode() == SC_OK)
                    return mapper.readValue(response.getEntity().getContent(), clazz);
                else
                    throw responseException(
                            response.getStatusLine().getStatusCode(),
                            EntityUtils.toString(response.getEntity())
                    );
            }
        };
    }

    public static <T> ResponseHandler<List<T>> jsonListResponse(final TypeReference<List<T>> typeReference) {
        return new ResponseHandler<List<T>>() {
            @Override
            public List<T> handleResponse(HttpResponse response) throws IOException {
                if (response.getStatusLine().getStatusCode() == SC_OK)
                    return mapper.readValue(response.getEntity().getContent(), typeReference);
                else
                    throw responseException(
                            response.getStatusLine().getStatusCode(),
                            EntityUtils.toString(response.getEntity())
                    );
            }
        };
    }

}
