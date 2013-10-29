package com.careeropts.rurse.web.resource;


import com.careeropts.rurse.model.User;
import com.careeropts.rurse.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

/**
 * Responsible for the management of user accounts
 *
 * @name Account Resource
 * @contextPath /rest
 */
@Component
@Path("/account")
public class AccountResource {

    @Autowired
    IUserService service;

    /**
     * Creates an account with the provided email address and password
     * @param email Email address for the new user account
     * @param password Password for the new user account
     * @return
     */
    @POST
    @Consumes({TEXT_PLAIN})
    public User createAccount (
            @QueryParam("email") String email,
            String password) {

        return service.createAccount(email, password);
    }
}
