package com.xu.atchat.controller;

import com.xu.atchat.advance.response.CommonResponse;
import com.xu.atchat.model.domain.Feedback;
import com.xu.atchat.service.IFeedbackService;
import com.xu.atchat.service.minio.MinIoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/9/20 12:52
 * @description
 */
@RestController
@RequestMapping("/atchat/feedback")
public class FeedbackController {


    @Autowired
    private MinIoService minIoService;

    @Autowired
    private IFeedbackService feedbackService;

    /**
     * 反馈文件上传
     * TODO 前端组件问题，导致无法使用 MultipartFile[]
     *
     * @param type
     * @return
     */
    @PostMapping("/upload/{type}")
    public Object uploadChatMessageFile(@RequestParam(name = "files0",required = false) MultipartFile files0,
                                        @RequestParam(name = "files1",required = false) MultipartFile files1,
                                        @RequestParam(name = "files2",required = false) MultipartFile files2,
                                        @RequestParam(name = "files3",required = false) MultipartFile files3,
                                        @RequestParam(name = "files4",required = false) MultipartFile files4,
                                        @RequestParam(name ="appid",required = false) String  appid,
                                        @RequestParam(name ="md",required = false) String md,
                                        @RequestParam(name ="app_version",required = false) String app_version,
                                        @RequestParam(name ="os",required = false) String os,
                                        @RequestParam(name ="content",required = false) String content,
                                        @RequestParam(name ="contact",required = false) String contact,
                                        @RequestParam(name ="score",required = false) String score,
                                        HttpServletRequest request,
                                        @PathVariable(name = "type")String type){
        MultipartFile[] files = {files0,files1,files2,files3,files4};
        List<MultipartFile> fileList = Arrays.asList(files).stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<String> paths = new ArrayList<>();
        fileList.stream().forEach(f ->{
            String s = minIoService.uploadFile(f);
            paths.add(s);
        });
        String filesJoin = paths.stream().collect(Collectors.joining(","));
        Feedback feedback = new Feedback();
        feedback.setAppid(appid);
        feedback.setMd(md);
        feedback.setAppVersion(app_version);
        feedback.setOs(os);
        feedback.setContact(contact);
        feedback.setScore(score);
        feedback.setFiles(filesJoin);
        feedback.setType(type);
        feedback.setContent(content);

        feedbackService.save(feedback);
        return new CommonResponse<>(paths);
    }
}
