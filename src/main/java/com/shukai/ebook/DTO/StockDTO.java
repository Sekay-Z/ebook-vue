package com.shukai.ebook.DTO;

public class StockDTO {
    private String ISBN;
    private int quantity;

    public StockDTO(String ISBN, int quantity) {
        this.ISBN = ISBN;
        this.quantity = quantity;
    }
}
