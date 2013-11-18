package com.careeropts.rurse.client;


import com.careeropts.rurse.client.exception.RurseAppException;
import com.careeropts.rurse.client.exception.UnauthorizedException;
import com.careeropts.rurse.client.exception.UriException;
import com.careeropts.rurse.client.impl.ManagerOperations;
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
import static com.careeropts.rurse.client.util.Requests.postText;
import static com.careeropts.rurse.client.util.ResponseHandlers.jsonResponse;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.http.client.utils.URIUtils.extractHost;

/**
 * Primary API for interacting the RURSE system.  This class takes care of all configurations for the REST client to
 * enable valid interactions with the REST API.
 *
 * This class acts as a factory class which allows the creation of classes that are responsible for interacting
 * with the system for various user privilege levels.  All operations instances will share a common {@link HttpClient}
 * implementation.  This client defaults to allow 20 concurrent connections so applications which have different
 * needs should provide their own instance.
 *
 * All operations instances are self contained, so that it is safe to generate multiple operations instances with
 * different account information.
 *
 * This API is thread safe.
 */
public class RurseApplication {

    private static String DEFAULT_BASE_URL = "http://rurse.careeropts.com:8080";

    final private String baseUrl;
    final private CloseableHttpClient client;

    private static CloseableHttpClient defaultClient() {
        return  HttpClients.custom()
                .setDefaultSocketConfig(SocketConfig
                        .custom()
                        .setSoTimeout(60000) //default timeout.
                        .build())
                .build();
    }

    /**
     * Builds an Http Context to allow for authentication to occur in rest requests.
     */
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

    public RurseApplication(CloseableHttpClient client) {
        this(DEFAULT_BASE_URL, client);
    }

    public RurseApplication(String baseUrl, CloseableHttpClient client) {
        notEmpty(baseUrl);
        notNull(client);

        this.baseUrl = baseUrl;
        this.client = client;
    }

    /**
     * Creates a new account in the RURSE system with the given email and password.
     * @param email Email address for the user to create an account for.
     * @param password Password for the new account.
     */
    public User createAccount(String email, String password) {
        notNull(email);
        notNull(password);
        try {
            URI uri = builder(baseUrl, Endpoints.ACCOUNT_ENDPOINT)
                    .addParameter("email", email)
                    .build();

            return client.execute(
                    postText(uri, JSON, password),
                    jsonResponse(User.class)
            );

        } catch (URISyntaxException e) {
            throw new UriException( e);
        } catch (IOException e) {
            throw new RurseAppException(e);
        }
    }

    /**
     * Creates a {@link IUserOperations} object for the user with the provided credentials.  This will allow the
     * user to interact with the basic user operation in the RURSE system.
     *
     * @param email Email address of the user using the RURSE system.
     * @param password Password for the user.
     */
    public IUserOperations userOperations(String email, String password) {
        HttpContext context = generateContext(baseUrl, email, password);

        UserOperations userOps = new UserOperations(baseUrl, client, context);

        //make this call to verify the user is in the system.
        userOps.getUserInfo();

        return userOps;
    }

    /**
     * Creates a {@link IManagerOperations} object for the user with the provided credentials.  This will allow the
     * user to interact with the manager operation in the RURSE system.
     *
     * @param email Email address of the user using the RURSE system.
     * @param password Password for the user.
     */
    public IManagerOperations managerOperations(String email, String password) {
        HttpContext context = generateContext(baseUrl, email, password);

        ManagerOperations managerOps = new ManagerOperations(baseUrl, client, context);

        //make this call to verify the user is in the system.
        User user = managerOps.getUserInfo();

        //verify they are a manager.
        if (!user.isManager())
            throw new UnauthorizedException("The provided email address is not a valid manager in the system");

        return managerOps;
    }

    public void close() throws IOException {
        client.close();
    }
}
