package com.careeropts.rurse.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    Long id;
    String email;
    UserType type;
    Resume resume;

    public User() {
    }

    public User(Long id, String email, UserType type, Resume resume) {
        this.id = id;
        this.email = email;
        this.type = type;
        this.resume = resume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (resume != null ? !resume.equals(user.resume) : user.resume != null) return false;
        if (type != user.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (resume != null ? resume.hashCode() : 0);
        return result;
    }

    public static enum UserType {
        Basic("basic"),
        Manager("manager");

        private final String name;

        private UserType(String name) {
            this.name = name;
        }

        public static UserType fromName(String name) {
            if (name == null)
                return null;

            for (UserType userType : UserType.values())
                if (userType.name().equals(name))
                    return userType;

            return null;
        }
    }
}
