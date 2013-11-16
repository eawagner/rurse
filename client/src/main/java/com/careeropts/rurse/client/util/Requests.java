package com.careeropts.rurse.client.util;


import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;

import java.io.InputStream;
import java.net.URI;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

public class Requests {

    private Requests() {/*private constructor*/}

    public static final String JSON = "application/json";
    public static final String OCTET_STREAM = "application/octet-stream";

    public static HttpGet get(URI uri) {
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

    public static HttpPost post(URI uri, String accept, String contentType, InputStream input) {
        HttpPost method = post(uri, accept);

        if (!isEmpty(contentType))
            method.setHeader(CONTENT_TYPE, contentType);

        method.setEntity(new InputStreamEntity(input));

        return method;
    }

    public static HttpDelete delete(URI uri) {
        return new HttpDelete(uri);
    }

}
