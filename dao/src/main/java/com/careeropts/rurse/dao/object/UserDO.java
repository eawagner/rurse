package com.careeropts.rurse.dao.object;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "User")
public class UserDO {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, unique = true)
    Long id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    private boolean manager;

    @OneToOne(fetch = LAZY, cascade = ALL, orphanRemoval = true)
    ResumeDO resume;

    public UserDO() {
    }

    public UserDO(String email, String password, boolean manager, ResumeDO resume) {
        this.email = email;
        this.password = password;
        this.manager = manager;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public ResumeDO getResume() {
        return resume;
    }

    public void setResume(ResumeDO resume) {
        this.resume = resume;
    }
}
