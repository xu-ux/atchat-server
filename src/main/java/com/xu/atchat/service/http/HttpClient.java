package com.xu.atchat.service.http;

import com.alibaba.fastjson.JSONObject;
import com.xu.atchat.service.http.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/8/30 10:45
 * @description
 */
@Slf4j
public class HttpClient{

    private static final RequestConfig requestConfig;

    private static final RequestConfig uploadConfig;

    static {
        requestConfig = RequestConfig.custom()
                // 客户端和服务器建立连接的timeout
                .setConnectTimeout(1000*60)
                // 指从连接池获取连接的timeout
                .setConnectionRequestTimeout(6000)
                // 客户端从服务器读取数据的timeout
                .setSocketTimeout(1000*60*3)
                .build();
        uploadConfig = RequestConfig.custom()
                // 客户端和服务器建立连接的timeout
                .setConnectTimeout(1000*60*20)
                // 指从连接池获取连接的timeout
                .setConnectionRequestTimeout(6000)
                // 客户端从服务器读取数据的timeout
                .setSocketTimeout(1000*60*20)
                .build();
    }

    /**
     * map转换
     */
    public static Map<String, String> convertMap(JSONObject jsonObject) throws Exception{
        Map<String, Object> innerMap = jsonObject.getInnerMap();
        Map<String, String> map = convertMap(innerMap);
        return map;
    }

    /**
     * map转换
     */
    public static Map<String, String> convertMap(Map<String, Object> objectMap) throws Exception{
        Map<String, String> map = new HashMap<>();
        for(Map.Entry<String, Object> entries : objectMap.entrySet()){
            map.put(entries.getKey(),null == entries.getValue() ? null : String.valueOf(entries.getValue()));
        }
        return map;
    }

    /**
     * 发送get请求
     * @param url 访问地址
     * @param param query参数
     * @return
     */
    public static JSONObject doGet(String url, Map<String, String> param) throws HttpException{

        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            log.debug("-->>HTTP GET请求地址：{}",url);
            log.debug("-->>HTTP 请求参数：{}",param.toString());
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
                log.info("<<--HTTP 响应内容：[{}]",resultString);
            }else{
                log.error("<<--HTTP 响应状态码：[{}]",response.getStatusLine().getStatusCode());
                throw new HttpException.OperationFailure("请求失败");
            }

        } catch (IOException | URISyntaxException e) {
            log.error("HTTP 发送请求异常",e);
            throw new HttpException.OperationFailure("发送请求失败");
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                log.error("HTTP 关闭流异常",e);
            }
        }
        return JSONObject.parseObject(resultString);
    }

    /**
     * 发送post请求
     * @param url 请求地址
     * @param param 表单参数
     * @return
     */
    public static String doPost(String url, Map<String, String> param) throws HttpException{
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            log.debug("-->>HTTP POST请求地址：{}",url);
            log.debug("-->>HTTP 请求参数：{}",param.toString());
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
                log.info("<<--HTTP 响应内容：",resultString);
            }else{
                log.error("<<--HTTP 响应状态码：",response.getStatusLine().getStatusCode());
                throw new HttpException.OperationFailure("请求失败");
            }
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (IOException e) {
            log.error("HTTP 发送请求异常",e);
            throw new HttpException.OperationFailure("发送请求失败");
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                log.error("HTTP 关闭流异常",e);
            }
        }
        return resultString;
    }

    /**
     * 发送post请求
     *
     * @param url 请求地址
     * @param json json入参
     * @return
     */
    public static JSONObject doPostJson(String url, String json) throws HttpException{
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            log.debug("-->>HTTP POST请求地址：{}",url);
            log.debug("-->>HTTP 请求参数：{}",json);
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            httpPost.setConfig(requestConfig);
            // 执行http请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
                log.info("<<--HTTP 响应内容：",resultString);
            }else{
                log.error("<<--HTTP 响应状态码：",response.getStatusLine().getStatusCode());
                throw new HttpException.OperationFailure("请求失败");
            }
        } catch (IOException e) {
            log.error("HTTP 发送请求异常",e);
            throw new HttpException.OperationFailure("发送请求失败");
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                log.error("HTTP 关闭流异常",e);
            }
        }
        return JSONObject.parseObject(resultString);
    }

}
