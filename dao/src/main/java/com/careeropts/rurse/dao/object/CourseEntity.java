package com.careeropts.rurse.dao.object;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "Course")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, unique = true)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String description;

    Double cost;

    String duration;

    public CourseEntity() {
    }

    public CourseEntity(Long id,
                        String title,
                        String description,
                        Double cost,
                        String duration) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.duration = duration;
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
