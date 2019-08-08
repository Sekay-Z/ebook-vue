package com.shukai.ebook.bean;

import java.util.Date;

public class Order_Detail {
    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public double getBook_price() {
        return book_price;
    }

    public void setBook_price(double book_price) {
        this.book_price = book_price;
    }

    public Integer getBook_quantity() {
        return book_quantity;
    }

    public void setBook_quantity(Integer book_quantity) {
        this.book_quantity = book_quantity;
    }

    public String getBook_icon() {
        return book_icon;
    }

    public void setBook_icon(String book_icon) {
        this.book_icon = book_icon;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    private String detail_id;
    private String order_id;
    private String ISBN;
    private String book_name;
    private String book_author;
    private double book_price;
    private Integer book_quantity;
    private String book_icon;
    private Date create_time;
    private Date update_time;
}
