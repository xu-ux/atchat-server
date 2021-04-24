package com.xu.atchat.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/9/19 9:13
 * @description
 */
@Slf4j
public class FreeMarkerUtils {

    private static Configuration cfg = new Configuration(Configuration.getVersion());

    static {
        cfg.setEncoding(Locale.CHINA, "utf-8");
        cfg.setClassForTemplateLoading(FreeMarkerUtils.class, "/templates");
    }

    /**
     * 获取模板
     *
     * @param templateName
     * @return
     */
    public static Template getTpl(String templateName){
        try {
            Template template = cfg.getTemplate(templateName);
            return template;
        } catch (Exception e) {
            log.error("获取模板失败 {}",templateName,e);
            return null;
        }
    }

    /**
     * 获取模板写入后的内容
     *
     * @param templateName
     * @param model
     * @return
     */
    public static String getTplText(String templateName, Map<String, Object> model){
        try {
            Template template = cfg.getTemplate(templateName);
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            return text;
        } catch (Exception e) {
            log.error("获取模板内容失败 {}",templateName,e);
            return null;
        }
    }
}
