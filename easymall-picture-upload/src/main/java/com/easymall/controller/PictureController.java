package com.easymall.controller;

import com.easymall.common.vo.PicUploadResult;
import com.easymall.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PictureController {
    @Autowired
    private PictureService pictureService;
    @RequestMapping("pic/uploadImg")
    public PicUploadResult pictureUpload(MultipartFile pic){
        System.out.println("uploadImg------");
        return pictureService.pictureUpload(pic);
    }

}
