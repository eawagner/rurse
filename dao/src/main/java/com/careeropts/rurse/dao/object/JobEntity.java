package com.careeropts.rurse.dao.object;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "Job")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private String location;

    private String city;

    private String state;

    @Column(nullable = false)
    private boolean active;

    public JobEntity() {
    }

    public JobEntity(Long id,
                     String title,
                     String description,
                     String location,
                     String city,
                     String state,
                     boolean active) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.city = city;
        this.state = state;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
