package com.careeropts.rurse.client;


import com.careeropts.rurse.client.exception.UriException;
import com.careeropts.rurse.client.impl.MangerOperations;
import com.careeropts.rurse.client.impl.UserOperations;
import com.careeropts.rurse.client.util.Endpoints;
import com.careeropts.rurse.model.User;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.careeropts.rurse.client.util.Endpoints.builder;
import static com.careeropts.rurse.client.util.Requests.JSON;
import static com.careeropts.rurse.client.util.Requests.post;
import static com.careeropts.rurse.client.util.ResponseHandlers.jsonResponse;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.http.client.utils.URIUtils.extractHost;

public class RurseApplication {

    private static String DEFAULT_BASE_URL = "http://localhost:8080";

    final private String baseUrl;
    final private HttpClient client;

    private static CloseableHttpClient defaultClient() {
        return  HttpClients.custom()
                .setDefaultSocketConfig(SocketConfig
                        .custom()
                        .setSoTimeout(60000) //default timeout.
                        .build())
                .build();
    }

    private static HttpContext generateContext(String baseUrl, String email, String password) {
        notNull(email);
        notNull(password);
        try {
            AuthCache authCache = new BasicAuthCache();
            authCache.put(extractHost(builder(baseUrl, "").build()), new BasicScheme());

            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(
                    AuthScope.ANY,
                    new UsernamePasswordCredentials(email, password)
            );

            HttpClientContext context = HttpClientContext.create();
            context.setCredentialsProvider(credentialsProvider);
            context.setAuthCache(authCache);
            return context;

        } catch (URISyntaxException e) {
            throw new UriException(e);
        }
    }

    public RurseApplication() {
        this(DEFAULT_BASE_URL);
    }

    public RurseApplication(String baseUrl) {
        this(baseUrl, defaultClient());
    }

    public RurseApplication(HttpClient client) {
        this(DEFAULT_BASE_URL, client);
    }

    public RurseApplication(String baseUrl, HttpClient client) {
        notEmpty(baseUrl);
        notNull(client);

        this.baseUrl = baseUrl;
        this.client = client;
    }

    public User createAccount(String email, String password) {
        notNull(email);
        notNull(password);
        try {
            URI uri = builder(baseUrl, Endpoints.ACCOUNT_ENDPOINT)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .build();

            return client.execute(
                    post(uri, JSON),
                    jsonResponse(User.class)
            );

        } catch (URISyntaxException e) {
            throw new UriException( e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public IUserOperations userOperations(String email, String password) {
        HttpContext context = generateContext(baseUrl, email, password);

        UserOperations userOps = new UserOperations(baseUrl, client, context);

        //make this call to verify the user is in the system.
        userOps.getUserInfo();

        return userOps;
    }

    public IManagerOperations managerOperations(String email, String password) {
        HttpContext context = generateContext(baseUrl, email, password);

        MangerOperations managerOps = new MangerOperations(baseUrl, client, context);

        //make this call to verify the user is in the system.
        User user = managerOps.getUserInfo();

        //verify they are a manager.
        if (!user.isManager())
            throw new RuntimeException(); //TODO throw meaningful exception

        return new MangerOperations(baseUrl, client, context);
    }
}
