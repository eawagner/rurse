package com.careeropts.rurse.dao.object;

public class CourseDO {

    String id;
    String title;
    String description;
    String cost;
    String duration;

    public CourseDO() {
    }

    public CourseDO(String id,
                    String title,
                    String description,
                    String cost,
                    String duration) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
