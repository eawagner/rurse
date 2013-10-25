package com.careeropts.rurse.model;

public class Job {

    private String id;
    private String title;
    private String description;
    private String location;
    private String city;
    private String state;
    private boolean active;

    public Job() {
    }

    public Job(String id,
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        if (active != job.active) return false;
        if (city != null ? !city.equals(job.city) : job.city != null) return false;
        if (description != null ? !description.equals(job.description) : job.description != null) return false;
        if (id != null ? !id.equals(job.id) : job.id != null) return false;
        if (location != null ? !location.equals(job.location) : job.location != null) return false;
        if (state != null ? !state.equals(job.state) : job.state != null) return false;
        if (title != null ? !title.equals(job.title) : job.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}
