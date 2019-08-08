package com.shukai.ebook.dao;

import com.shukai.ebook.DTO.StockDTO;
import com.shukai.ebook.bean.Book;

import java.util.List;

public interface BookDao {
    public Book getBookByISBN(String ISBN);
    public List<Book> getBooks();
    public List<Book> searchBook(String name);
    public int deleteBook(String ISBN);
    public void addBook(Book book);
    public int modifyBook(Book book);
    public void decreaseStock(StockDTO stockDTO);
    public void increaseStock(StockDTO stockDTO);
}
