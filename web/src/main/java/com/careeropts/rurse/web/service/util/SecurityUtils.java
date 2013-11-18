package com.careeropts.rurse.web.service.util;

import org.springframework.security.core.Authentication;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * Security utility methods
 */
public class SecurityUtils {
    private SecurityUtils() {/*private constructor*/}

    /**
     * Gets the current username from the security context
     */
    public static String getAuthenticatedUser() {
        Authentication auth = getContext().getAuthentication();
        if (auth != null)
            return auth.getName();

        return null;
    }

}
