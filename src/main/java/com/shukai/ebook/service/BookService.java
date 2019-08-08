package com.shukai.ebook.service;

import com.shukai.ebook.bean.Book;
import com.shukai.ebook.bean.Order_Master;
import com.shukai.ebook.exception.OrderException;

import java.util.List;

public interface BookService {
    public Book getBookByISBN(String ISBN);
    public List<Book> getBooks();
    public List<Book> searchBook(String name);
    public int deleteBook(String ISBN);
    public void addBook(Book book);
    public int modifyBook(Book book);
    public void decreaseStock(Order_Master order_master) throws OrderException;
    public void increaseStock(Order_Master order_master);
}
