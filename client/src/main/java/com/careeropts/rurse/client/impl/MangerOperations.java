package com.careeropts.rurse.client.impl;


import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HttpContext;

public class MangerOperations extends UserOperations {

    public MangerOperations(String baseUrl, HttpClient client, HttpContext context) {
        super(baseUrl, client, context);
    }
}
