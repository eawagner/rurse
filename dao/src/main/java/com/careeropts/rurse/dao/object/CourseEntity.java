package com.careeropts.rurse.dao.object;

import org.apache.solr.analysis.*;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;
import static org.hibernate.search.annotations.Index.YES;
import static org.hibernate.search.annotations.Store.NO;

@Entity
@Table(name = "Course")
@Indexed
@AnalyzerDef(name = "courseAnalyzer",
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
@Analyzer(definition = "courseAnalyzer")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, unique = true)
    Long id;

    @Column(nullable = false)
    @Boost(2.0f)
    @Field(index = YES, store = NO)
    String title;

    @Column(nullable = false)
    @Field(index = YES, store = NO)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseEntity that = (CourseEntity) o;

        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

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
