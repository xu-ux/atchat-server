package com.xu.atchat.controller;

import com.xu.atchat.advance.response.CommonResponse;
import com.xu.atchat.service.minio.MinIoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/9/20 9:55
 * @description
 */
@RestController
@RequestMapping("/atchat/file")
public class FileController {

    @Autowired
    private MinIoService minIoService;

    /**
     * 聊天文件上传
     * @param files
     * @param type
     * @return
     */
    @PostMapping("/upload/{type}")
    public Object uploadChatMessageFile(@RequestParam("file") MultipartFile file, @PathVariable(name = "type")String type){
        String path = "error";
        switch (type){
            case "chat":
                    path = minIoService.uploadFile(file);
                break;
            default:
                break;
        }
        return new CommonResponse<>(path);
    }

    /**
     * 聊天文件上传
     * @param files
     * @param type
     * @return
     */
    @PostMapping("/uploads/{type}")
    public Object uploadChatMessageFiles(@RequestParam("files") MultipartFile[] files, @PathVariable(name = "type")String type){
        List<String> paths = new ArrayList<>();
        switch (type){
            case "chat":
                for (MultipartFile file: files) {
                    String path = minIoService.uploadFile(file);
                    paths.add(path);
                }
                break;
            default:
                break;
        }
        return new CommonResponse<>(paths);
    }
}
