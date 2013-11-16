package com.careeropts.rurse.client.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;

import java.io.InputStream;
import java.net.URI;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.entity.ContentType.*;

public class Requests {
    private Requests() {/*private constructor*/}

    private static ObjectMapper mapper = new ObjectMapper();

    public static final String JSON = APPLICATION_JSON.getMimeType();

    public static HttpGet getAny(URI uri) {
        return get(uri, null);
    }

    public static HttpGet get(URI uri, String accept) {
        HttpGet method = new HttpGet(uri);
        if (!isEmpty(accept))
            method.setHeader(ACCEPT, accept);

        return method;
    }

    public static HttpPost post(URI uri) {
        return post(uri, null);
    }

    public static HttpPost post(URI uri, String accept) {
        HttpPost method = new HttpPost(uri);

        if (!isEmpty(accept))
            method.setHeader(ACCEPT, accept);

        return method;
    }

    public static HttpPost postStream(URI uri, String accept, InputStream input) {
        HttpPost method = post(uri, accept);
        method.setEntity(new InputStreamEntity(
                input,
                APPLICATION_OCTET_STREAM
        ));
        return method;
    }

    public static <T> HttpPost postJson(URI uri, String accept, T data) throws JsonProcessingException {
        HttpPost method = post(uri, accept);
        method.setEntity(new StringEntity(
                mapper.writeValueAsString(data),
                APPLICATION_JSON
        ));

        return method;
    }

    public static <T> HttpPost postText(URI uri, String accept, String data) throws JsonProcessingException {
        HttpPost method = post(uri, accept);
        method.setEntity(new StringEntity(
                data,
                TEXT_PLAIN
        ));

        return method;
    }

    public static HttpDelete delete(URI uri) {
        return new HttpDelete(uri);
    }
}
