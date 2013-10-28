package com.careeropts.rurse.dao.object;


import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Resolution;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.TemporalType.DATE;
import static org.hibernate.search.annotations.Resolution.DAY;

@Entity
@Table(name = "Book")
public class BookDO {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, unique = true)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String description;

    String publisher;

    @Temporal(DATE)
    @DateBridge(resolution = DAY)
    Date publishDate;

    Double price;

    String isbn;

    public BookDO() {
    }

    public BookDO(Long id,
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
}
