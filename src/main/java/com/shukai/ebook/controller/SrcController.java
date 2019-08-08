package com.shukai.ebook.controller;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static javax.imageio.ImageIO.read;

@RestController
@RequestMapping(value = "/src")
public class SrcController {
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;

    @PostMapping(value = "/upload",produces = {"application/json;charset=UTF-8"})
    public String upload(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        String filename=file.getOriginalFilename();
        if(file.getSize()>500*1024){
            return "文件过大!";
        }
        Query query=Query.query(Criteria.where("filename").is(filename));
        if(mongoTemplate.exists(query,"fs.files")){
            return "文件名重复!";
        }
        try {
            Image image = read(file.getInputStream());
            if (image == null)
                return "不支持的文件类型!";
        } catch (IOException e) {
            return "不支持的文件类型!";
        }
        session.setAttribute("file",file.getInputStream());
        return filename;
    }
    @GetMapping("/downloadFile/{filename:.+}")
    public void downloadFile(@PathVariable("filename") String filename, HttpServletResponse response) throws IOException {
        Query query=Query.query(Criteria.where("filename").is(filename));
        //从mongodb查出相应文件
        GridFSFile gridFSFile=gridFsTemplate.findOne(query);
        //获取下载流对象
        GridFSDownloadStream downloadStream=gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //获取流对象
        GridFsResource gridFsResource=new GridFsResource(gridFSFile,downloadStream);
        //获取数据流
        InputStream inputStream=gridFsResource.getInputStream();
        BufferedImage bufferedImage= ImageIO.read(inputStream);
        ImageIO.write(bufferedImage,"jpg",response.getOutputStream());
    }

    @GetMapping(value = "/deleteImage/{filename:.+}")
    public void deleteImage(@PathVariable("filename") String filename,HttpSession session){
        session.setAttribute("deleteFile",filename);
    }
}
