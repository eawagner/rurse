package com.careeropts.rurse.web.security;

import com.careeropts.rurse.dao.IUserDao;
import com.careeropts.rurse.dao.object.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Implementation of {@link UserDetailsService} to allow Spring to hook into the user data in the system for authentication
 * and role management.
 */
@Component("userSecurityService")
public class UserDetailsSecurityService implements UserDetailsService {

    private final IUserDao dao;

    @Autowired
    public UserDetailsSecurityService(IUserDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = dao.getByEmail(username);
        if (userEntity == null)
            throw new UsernameNotFoundException("Unable to find user: " + username);

        Collection<SimpleGrantedAuthority> auths = new ArrayList<>();

        //All found users are granted the user role.
        auths.add(new SimpleGrantedAuthority("ROLE_USER"));

        //A manager is also given the manager role.
        if (userEntity.isManager())
            auths.add(new SimpleGrantedAuthority("ROLE_MANAGER"));

        return new User(username, userEntity.getPassword(), auths);
    }
}
