package com.careeropts.rurse.model;


import java.util.Date;

public class Book {

    String id;
    String title;
    String description;
    String publisher;
    Date publishDate;
    Double price;
    String ISBN;


    public Book() {
    }

    public Book(String id,
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (ISBN != null ? !ISBN.equals(book.ISBN) : book.ISBN != null) return false;
        if (description != null ? !description.equals(book.description) : book.description != null) return false;
        if (id != null ? !id.equals(book.id) : book.id != null) return false;
        if (price != null ? !price.equals(book.price) : book.price != null) return false;
        if (publishDate != null ? !publishDate.equals(book.publishDate) : book.publishDate != null) return false;
        if (publisher != null ? !publisher.equals(book.publisher) : book.publisher != null) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;

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
        result = 31 * result + (ISBN != null ? ISBN.hashCode() : 0);
        return result;
    }
}
