package com.example.qualityreads;

public class Book {
    String id;
    String name;
    String isbn;
    String category;
    String pagination;
    String publisher;
    String publishDate;
    double price;
    String description;
    String image;

    public Book(String id, String name, String isbn, double price, String image) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.price = price;
        this.image = image;
    }

    public Book(String id, String name, String isbn, String category, String pagination, String publisher, String publishDate, double price, String description, String image) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.category = category;
        this.pagination = pagination;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPagination() {
        return pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
