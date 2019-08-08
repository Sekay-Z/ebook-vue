package com.shukai.ebook;

import com.mongodb.gridfs.GridFSFile;
import com.shukai.ebook.bean.Book;
import com.shukai.ebook.service.BookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EbookApplicationTests {
	@Autowired
	private BookServiceImpl bookService;
	@Autowired
	private GridFsTemplate gridFsTemplate;

	@Test
	public void contextLoads() {
		gridFsTemplate.delete(Query.query(Criteria.where("filename").is("th.jpg")));

	}

}
