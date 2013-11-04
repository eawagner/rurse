package com.careeropts.rurse.dao.object;

import org.apache.solr.analysis.*;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;
import static org.hibernate.search.annotations.Index.YES;
import static org.hibernate.search.annotations.Store.NO;

@Entity
@Table(name = "Job")
@Indexed
@AnalyzerDef(name = "jobAnalyzer",
        tokenizer = @TokenizerDef(factory = LowerCaseTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = StandardFilterFactory.class),
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = StopFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @Parameter(name = "language", value = "English")
                }),
                @TokenFilterDef(factory = LengthFilterFactory.class, params = {
                        @Parameter(name = "min", value = "2"),
                        @Parameter(name = "max", value = "100")
                })
        }
)
@Analyzer(definition = "jobAnalyzer")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    @Boost(2.0f)
    @Field(index = YES, store = NO)
    private String title;

    @Column(nullable = false)
    @Field(index = YES, store = NO)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobEntity jobEntity = (JobEntity) o;

        if (active != jobEntity.active) return false;
        if (city != null ? !city.equals(jobEntity.city) : jobEntity.city != null) return false;
        if (description != null ? !description.equals(jobEntity.description) : jobEntity.description != null)
            return false;
        if (id != null ? !id.equals(jobEntity.id) : jobEntity.id != null) return false;
        if (location != null ? !location.equals(jobEntity.location) : jobEntity.location != null) return false;
        if (state != null ? !state.equals(jobEntity.state) : jobEntity.state != null) return false;
        if (title != null ? !title.equals(jobEntity.title) : jobEntity.title != null) return false;

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
