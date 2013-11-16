package com.careeropts.rurse.client.util;


import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;

import static java.lang.String.format;

public class Endpoints {
    private Endpoints() {/*private constructor*/}

    private static final String REST_BASE = "/web-service/rest";

    public static final String ACCOUNT_ENDPOINT = REST_BASE + "/account";

    public static final String BOOK_ENDPOINT = REST_BASE + "/book";
    public static final String SINGLE_BOOK_ENDPOINT = REST_BASE + "/book/%d";

    public static final String COURSE_ENDPOINT = REST_BASE + "/course";
    public static final String SINGLE_COURSE_ENDPOINT = REST_BASE + "/course/%d";

    public static final String JOB_ENDPOINT = REST_BASE + "/job";
    public static final String SINGLE_JOB_ENDPOINT = REST_BASE + "/job/%d";

    public static final String USER_ENDPOINT = REST_BASE + "/user";
    public static final String CURRENT_USER_ENDPOINT = USER_ENDPOINT + "/current";
    public static final String CURRENT_USER_RESUME_ENDPOINT = CURRENT_USER_ENDPOINT + "/resume";

    public static final String RECOMMENDED_BOOK_ENDPOINT = CURRENT_USER_ENDPOINT + "/recommend/book";
    public static final String RECOMMENDED_COURSE_ENDPOINT = CURRENT_USER_ENDPOINT + "/recommend/course";
    public static final String RECOMMENDED_JOB_ENDPOINT = CURRENT_USER_ENDPOINT + "/recommend/job";

    public static URIBuilder builder(String baseUrl, String endpoint, Object... pathParams) throws URISyntaxException {
        return new URIBuilder(format(baseUrl + endpoint, pathParams));
    }

}
