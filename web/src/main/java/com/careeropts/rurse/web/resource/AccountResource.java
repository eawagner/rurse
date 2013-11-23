package com.careeropts.rurse.web.resource;


import com.careeropts.rurse.model.User;
import com.careeropts.rurse.web.exception.InternalServerError;
import com.careeropts.rurse.web.exception.WebAppResponseException;
import com.careeropts.rurse.web.service.IUserService;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.*;
import static org.apache.commons.logging.LogFactory.getLog;

/**
 * Responsible for the management of user accounts
 *
 * @name Account Resource
 * @contextPath /rest
 */
@Component
@Path("/account")
public class AccountResource {

    private static final Log logger = getLog(AccountResource.class);

    @Autowired
    IUserService service;

    /**
     * Creates an account with the provided email address and password
     * @param email Email address for the new user account
     * @param password Password for the new user account
     */
    @POST
    @Consumes({TEXT_PLAIN})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public User createAccount (
            @QueryParam("email") String email,
            String password) {
        try {

            return service.createAccount(email, password);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError();
        }
    }
}
