package com.easymall.service;

import com.easymall.common.utils.UploadUtil;
import com.easymall.common.vo.PicUploadResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class PictureService {
    public PicUploadResult pictureUpload(MultipartFile picture) {
        PicUploadResult picUploadResult=new PicUploadResult();
        String uuid= UUID.randomUUID().toString();
        String oldName=picture.getOriginalFilename();
        String ext=oldName.substring(oldName.lastIndexOf("."));
        if(!ext.matches(".(jpg|gif|png)")){
            picUploadResult.setError(1);
            return picUploadResult;
        }
        String newName=uuid+ext;
        String path0=UploadUtil.getUploadPath(newName,"upload");
        String path= "D:/easymall/img/"+path0+"/";
        File dir=new File(path);
        if (!dir.exists()){
            dir.mkdirs();
        }
        try {
            picture.transferTo(new File(path+newName));
        } catch (IOException e) {
            e.printStackTrace();
            picUploadResult.setError(1);
            return picUploadResult;
        }
        String imgUrl="http://image.jt.com/"+path0+"/"+newName;
        picUploadResult.setUrl(imgUrl);
        return picUploadResult;
    }
}
