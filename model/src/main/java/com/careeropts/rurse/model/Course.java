package com.careeropts.rurse.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlRootElement;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class Course {

    Long id;
    String title;
    String description;
    Double cost;
    String duration;

    public Course() {
    }

    public Course(Long id,
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (cost != null ? !cost.equals(course.cost) : course.cost != null) return false;
        if (description != null ? !description.equals(course.description) : course.description != null) return false;
        if (duration != null ? !duration.equals(course.duration) : course.duration != null) return false;
        if (id != null ? !id.equals(course.id) : course.id != null) return false;
        if (title != null ? !title.equals(course.title) : course.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        return result;
    }
}
