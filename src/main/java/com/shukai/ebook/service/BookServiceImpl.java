package com.shukai.ebook.service;

import com.shukai.ebook.DTO.StockDTO;
import com.shukai.ebook.bean.Book;
import com.shukai.ebook.bean.Order_Detail;
import com.shukai.ebook.bean.Order_Master;
import com.shukai.ebook.dao.BookDao;
import com.shukai.ebook.exception.OrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public Book getBookByISBN(String ISBN) {
        return bookDao.getBookByISBN(ISBN);
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public List<Book> searchBook(String name) {
        return bookDao.searchBook(name);
    }

    @Override
    public int deleteBook(String ISBN) {
        return bookDao.deleteBook(ISBN);
    }

    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public int modifyBook(Book book) {
        return bookDao.modifyBook(book);
    }

    @Override
    public void decreaseStock(Order_Master order_master) throws OrderException {
        List<Order_Detail> list=order_master.getOrderDetailList();
        for(Order_Detail order_detail:list){
            String ISBN=order_detail.getISBN();
            int quantity=order_detail.getBook_quantity();
            int stock=bookDao.getBookByISBN(ISBN).getStock();
            int result=stock-quantity;
            if(result<0){
                throw new OrderException("库存量不够!");
            }else{
                bookDao.decreaseStock(new StockDTO(ISBN,result));
            }
        }
    }

    @Override
    public void increaseStock(Order_Master order_master) {
        List<Order_Detail> list=order_master.getOrderDetailList();
        for(Order_Detail order_detail:list){
            String ISBN=order_detail.getISBN();
            int quantity=order_detail.getBook_quantity();
            int stock=bookDao.getBookByISBN(ISBN).getStock();
            int result=stock+quantity;
            bookDao.increaseStock(new StockDTO(ISBN,result));
        }
    }

}
