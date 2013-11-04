package com.careeropts.rurse.dao.object;


import org.apache.solr.analysis.*;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.TemporalType.DATE;
import static org.hibernate.search.annotations.Index.YES;
import static org.hibernate.search.annotations.Store.NO;

@Entity
@Table(name = "Book")
@Indexed
@AnalyzerDef(name = "bookAnalyzer",
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
@Analyzer(definition = "bookAnalyzer")
public class BookEntity {

    @Id
    @DocumentId
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

    String publisher;

    @Temporal(DATE)
    Date publishDate;

    Double price;

    String isbn;

    public BookEntity() {
    }

    public BookEntity(Long id,
                      String title,
                      String description,
                      String publisher,
                      Date publishDate,
                      Double price,
                      String isbn) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.price = price;
        this.isbn = isbn;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookEntity that = (BookEntity) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        return result;
    }
}
