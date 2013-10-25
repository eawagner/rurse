package com.careeropts.rurse.dao.object;


import javax.persistence.*;
import java.util.Date;

@Entity
public class BookDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String description;

    String publisher;

    Date publishDate;

    Double price;

    String ISBN;

    public BookDO() {
    }

    public BookDO(Long id,
                  String title,
                  String description,
                  String publisher,
                  Date publishDate,
                  Double price,
                  String ISBN) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.price = price;
        this.ISBN = ISBN;
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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
