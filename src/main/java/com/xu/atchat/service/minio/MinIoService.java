package com.xu.atchat.service.minio;

import com.xu.atchat.advance.exception.BaseException;
import com.xu.atchat.config.MinIoProperties;
import com.xu.atchat.util.UUIDUtils;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/31 12:24
 * @description minio文件服务
 *
 */
@Slf4j
@Component
@Configuration
@EnableConfigurationProperties({MinIoProperties.class})
public class MinIoService {

    private MinIoProperties minIo;

    public MinIoService(MinIoProperties minIo) {
        this.minIo = minIo;
    }

    private MinioClient instance;

    // 50Mbit
    private static final PutObjectOptions options = new PutObjectOptions(-1, 50L*1024*1024);


    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        try {
            instance = new MinioClient(minIo.getEndpoint(),minIo.getAccessKey(),minIo.getSecretKey());
        } catch (InvalidPortException e) {
            e.printStackTrace();
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断 bucket存储桶是否存在
     * @param bucketName
     * @return
     */
    public boolean bucketExists(String bucketName){
        try {
            return instance.bucketExists(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建 bucket 存储桶
     * @param bucketName
     */
    public void makeBucket(String bucketName){
        try {
            boolean isExist = instance.bucketExists(bucketName);
            if(!isExist) {
                instance.makeBucket(bucketName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取全部 bucket 存储桶
     * @return
     */
    public List<Bucket> listBuckets() {
        try {
            return instance.listBuckets();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据bucketName获取信息
     * @param bucketName bucket名称
     */
    public Optional<Bucket> getBucket(String bucketName) {
        try {
            return instance.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findAny();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除 bucket 存储桶
     * @param bucketName
     */
    @SneakyThrows
    public void removeBucket(String bucketName) {
        instance.removeBucket(bucketName);
    }


    /**
     * 文件上传
     * @param bucketName
     * @param objectName
     * @param filename
     */
    @SneakyThrows
    public void putObject(String bucketName, String objectName, String filename){
        instance.putObject(bucketName,objectName,filename,options);
    }


    /**
     * 上传文件
     * 通过直链访问文件的前提是需要设置桶的权限为 minioClient.setBucketPolicy(BUCKET_NAME, "*.*", PolicyType.READ_ONLY);
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file){
        try {
            String BUCKET_NAME = "chat0";
            boolean b = bucketExists(BUCKET_NAME);
            if (!b){
                instance.makeBucket(BUCKET_NAME);
            }
            String filename = UUIDUtils.getId();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 设置存储对象名称
            String originalFilename = file.getOriginalFilename();
            String suffix  = "";
            try {
                int i = originalFilename.lastIndexOf('.');
                suffix = originalFilename.substring(i);
            } catch (Exception e) {
                log.error("",e);
            }
            String objectName = sdf.format(new Date()) + "/" + filename+suffix;
            // 使用putObject上传一个文件到存储桶中
            PutObjectOptions options1 = new PutObjectOptions(-1, 50L*1024*1024);
            options1.setContentType(file.getContentType());
            instance.putObject(BUCKET_NAME, objectName, file.getInputStream(),options1);
            return minIo.getEndpoint()+"/" + BUCKET_NAME + "/" + objectName;
        } catch (Exception e) {
            log.error("",e);
            return "error";
        }

    }

    /**
     * 文件上传
     * @param bucketName
     * @param objectName
     * @param stream
     */
    @SneakyThrows
    public void putObject(String bucketName, String objectName, InputStream stream){
        instance.putObject(bucketName,objectName,stream,options);
    }


    /**
     * 文件上传,返回url
     * @param bucketName
     * @param objectName
     * @param stream
     */
    public String resURLPutObject(String bucketName, String objectName, InputStream stream){
        try {
            instance.putObject(bucketName,objectName,stream,options);
            String url = instance.presignedGetObject(bucketName, objectName);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取文件外链
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires  几秒钟内过期;默认为7天
     * @return
     */
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        try {
            return instance.presignedGetObject(bucketName, objectName, expires);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件信息
     * @param bucketName
     * @param objectName
     * @return
     */
    public ObjectStat getObjectInfo(String bucketName, String objectName) {
        try {
            return instance.statObject(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取文件
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    public InputStream getObjectFile(String bucketName, String objectName) {
        try {
            return instance.getObject(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除文件
     * @param bucketName
     * @param objectName
     */
    public void removeObject(String bucketName, String objectName){
        try {
            instance.removeObject(bucketName,objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
