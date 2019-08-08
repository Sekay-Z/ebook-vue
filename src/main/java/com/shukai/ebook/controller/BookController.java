package com.shukai.ebook.controller;

import com.shukai.ebook.bean.Book;
import com.shukai.ebook.service.BookServiceImpl;
import com.shukai.ebook.util.KeyUtil;
import com.shukai.ebook.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookServiceImpl bookServiceImpl;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @GetMapping("/searchBook")
    public List<Book> searchBook(@RequestParam("name") String name){
        return bookServiceImpl.searchBook(name);
    }

    @GetMapping("/getBooks")
    public List<Book> getBooks(){
        return bookServiceImpl.getBooks();
    }

    @GetMapping("/getBookByISBN")
    public Book getBookByISBN(@RequestParam("ISBN")String ISBN){
        return bookServiceImpl.getBookByISBN(ISBN);
    }

    @PostMapping("/addBook")
    public Result addBook(@RequestBody Book book, HttpSession session){
        InputStream file= (InputStream) session.getAttribute("file");
        if(file!=null){
            gridFsTemplate.store(file,book.getCover());
            session.removeAttribute("file");
        }
        book.setISBN(KeyUtil.genUniqueKey());
        bookServiceImpl.addBook(book);
        if(bookServiceImpl.getBookByISBN(book.getISBN())!=null){
            Result.getInstance().setMessage("添加成功!");
            Result.getInstance().setObject(null);
            return Result.getInstance();
        }else{
            Result.getInstance().setMessage("添加失败!");
            Result.getInstance().setObject(null);
            return Result.getInstance();
        }
    }

    @GetMapping("/deleteBook")
    public Result deleteBook(@RequestParam("ISBN")String ISBN){
        Book book=bookServiceImpl.getBookByISBN(ISBN);
        if (book.getCover() != null){
            Query query = Query.query(Criteria.where("filename").is(book.getCover()));
            gridFsTemplate.delete(query);
        }
        int row=bookServiceImpl.deleteBook(ISBN);
        if(row>0){
            Result.getInstance().setMessage("删除成功!");
            return Result.getInstance();
        }else{
            Result.getInstance().setMessage("删除失败!");
            return Result.getInstance();
        }
    }

    @PostMapping("/modifyBook")
    public Result modifyBook(@RequestBody Book book,HttpSession session){
        String deleteFilename= (String) session.getAttribute("deleteFile");
        if(deleteFilename!=null){
            Query query=Query.query(Criteria.where("filename").is(deleteFilename));
            gridFsTemplate.delete(query);
            session.removeAttribute("deleteFile");
        }
        InputStream inputStream= (InputStream) session.getAttribute("file");
        if(inputStream!=null){
            gridFsTemplate.store(inputStream,book.getCover());
            session.removeAttribute("file");
        }

        int row=bookServiceImpl.modifyBook(book);
        if(row<=0){
            Result.getInstance().setMessage("修改失败!");
            return Result.getInstance();
        }else{
            Result.getInstance().setMessage("修改成功!");
            return Result.getInstance();
        }
    }
}
