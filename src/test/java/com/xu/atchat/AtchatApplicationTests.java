package com.xu.atchat;

import com.xu.atchat.advance.enums.result.OperationFailureResultEnum;
import com.xu.atchat.advance.exception.BaseException;
import com.xu.atchat.constant.FriendsRequestStatus;
import com.xu.atchat.mapper.FriendsRequestMapper;
import com.xu.atchat.mapper.UserMapper;
import com.xu.atchat.model.domain.FriendsRequest;
import com.xu.atchat.model.domain.User;
import com.xu.atchat.service.fastdfs.FastDFSClient;
import com.xu.atchat.util.DingtalkPushUtils;
import com.xu.atchat.util.FileUtils;
import com.xu.atchat.util.FreeMarkerUtils;
import com.xu.atchat.util.QRCodeUtils;
import com.xu.atchat.util.encrypt.DESEncrypet;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AtchatApplication.class})
class AtchatApplicationTests {


    @SneakyThrows
    @Test
    public void dingTalkTest2(){
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("title", "标题使用了FreeMarker");
        model.put("message","com.xu.test#function()");
        model.put("position","39行");
        model.put("content","com.xu.tes");
        model.put("nowDate",new Date());
        Configuration cfg = new Configuration(Configuration.getVersion());
        cfg.setEncoding(Locale.CHINA, "utf-8");
        cfg.setClassForTemplateLoading(AtchatApplicationTests.class, "/templates");
        Template template = cfg.getTemplate("dingtalk.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        boolean b1 = DingtalkPushUtils.sendMarkdown(text,"测试",true);
        System.out.println("是否成功："+b1);
    }


    @SneakyThrows
    @Test
    public void dingTalkTest3(){
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("title", "标题使用了FreeMarker");
        model.put("message","com.xu.test#function()");
        model.put("position","39行");
        model.put("content","com.xu.tes");
        model.put("nowDate",new Date());

        String tplText = FreeMarkerUtils.getTplText("dingtalk.ftl", model);

        boolean b1 = DingtalkPushUtils.sendMarkdown(tplText,"测试biaoti",true);
        System.out.println("是否成功："+b1);
    }


}
