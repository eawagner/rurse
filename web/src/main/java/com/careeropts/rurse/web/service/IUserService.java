package com.careeropts.rurse.web.service;


import com.careeropts.rurse.model.Resume;
import com.careeropts.rurse.model.User;

import javax.ws.rs.core.Response;
import java.io.InputStream;

public interface IUserService {

    public User createAccount (String email, String password);
    public User makeManager(String email, boolean promote);


    public User getUser(Long id);
    public Response getResumeResponse(Long id);
    public Iterable<User> query(String searchText, Integer pageNum, Integer perPage);
    public void delete(Long id);


    public void changePassword (String password);
    public User getCurrentUser();
    public Response getResumeResponse();
    public Resume saveResume(String name, String type, InputStream resumeData);
    public void deleteResume();
}
