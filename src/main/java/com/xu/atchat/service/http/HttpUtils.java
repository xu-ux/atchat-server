package com.xu.atchat.service.http;

import com.alibaba.fastjson.JSONObject;


import com.beust.jcommander.internal.Maps;
import com.xu.atchat.constant.http.ReqType;
import com.xu.atchat.service.http.param.JSONHelper;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.*;
import com.xu.atchat.service.http.exception.HttpException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @Author: xucl
 * @Date: 2020/8/6
 * @Description: <p></p>
 */
public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    private static final int MAX_TIMEOUT = 3000; //超时时间
    private static final int MAX_TOTAL=10; //最大连接数
    private static final int ROUTE_MAX_TOTAL=3; //每个路由基础的连接数
    private static final  int MAX_RETRY = 5; //重试次数
    private static PoolingHttpClientConnectionManager connMgr; //连接池
    private static HttpRequestRetryHandler retryHandler; //重试机制

    static {
        cfgPoolMgr();
        cfgRetryHandler();
    }

    public HttpUtils() {
    }

    /**
     * 通用发起请求
     *
     * @param reqType
     * @param url
     * @param paramStr
     * @param token
     * @return
     * @throws RuntimeException
     */
    public static JSONObject doCommHttp(ReqType reqType, String url, String paramStr, String token) throws HttpException {
        return sendHttp(reqType, url, buildCommHeader(token), paramStr);
    }


    /**
     * 文件上传
     *
     * @param reqType
     * @param url
     * @param param
     * @param contentMd5
     * @param contentType
     * @param token
     * @return
     * @throws RuntimeException
     */
    public static JSONObject doUploadHttp(ReqType reqType, String url, byte[] param, String contentMd5,
                                          String contentType, String token) throws HttpException {
        return sendHttp(reqType, url, buildUploadHeader(contentMd5, contentType), param);
    }

    /**
     * 获取token
     *
     * @return
     * @throws RuntimeException
     */
    public static JSONObject doGetToken(String url) throws HttpException{
        JSONObject json = doCommHttp(ReqType.GET, url, null, "");
        JSONObject jsonObject = JSONHelper.castDataJson(json, JSONObject.class);
        return jsonObject;
    }

    /**
     *  @description 创建PAAS服务鉴权访问请求头
     *
     */
    private static Map<String, String> buildCommHeader(String token) {
        Map<String, String> header = Maps.newHashMap();
        header.put("Token", token);
        header.put("Content-Type", "application/json");
        return header;
    }

    /**
     * @description 创建文件流上传 请求头
     *
     */
    private static Map<String, String> buildUploadHeader(String contentMd5, String contentType) {
        Map<String, String> header = Maps.newHashMap();
        header.put("Content-MD5", contentMd5);
        header.put("Content-Type", contentType);
        return header;
    }

    /**
     * 发送http请求
     * @param reqType
     * @param url
     * @param headers
     * @param param
     * @return
     * @throws RuntimeException
     */
    public static JSONObject sendHttp(ReqType reqType, String url, Map<String, String> headers, Object param)
            throws HttpException{
        HttpRequestBase reqBase = reqType.getHttpType(url);
        log.info("\n--->>HttpUtil 开始向地址[{}]发起 [{}] 请求",url,reqBase.getMethod());

        CloseableHttpClient httpClient = getHttpClient();

        //超时时间配置
        config(reqBase);

        //设置请求头
        if(MapUtils.isNotEmpty(headers)) {
            for(Map.Entry<String, String> entry :headers.entrySet()) {
                reqBase.setHeader(entry.getKey(), entry.getValue());
            }
        }

        //添加参数 参数是json字符串
        if(param != null && param instanceof String) {
            String paramStr = String.valueOf(param);
            log.info("--->>HttpUtil 请求参数为：{}", paramStr);
            ((HttpEntityEnclosingRequest) reqBase).setEntity(
                    new StringEntity(String.valueOf(paramStr), ContentType.create("application/json", "UTF-8")));
            //参数时字节流数组
        } else if(param != null && param instanceof byte[]) {
            byte[] paramBytes = (byte[])param;
            ((HttpEntityEnclosingRequest) reqBase).setEntity(new ByteArrayEntity(paramBytes));
        }


        //响应对象
        CloseableHttpResponse res = null;
        //响应内容
        String resCtx = null;
        try {
            //执行请求
            res = httpClient.execute(reqBase);
            log.info("--->>HttpUtil 执行请求完毕，响应状态：{}",res.getStatusLine());

            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
                throw new HttpException.NotFundData("--->>HttpUtil 没有访问权限:"+res.getStatusLine());
            }else if (res.getStatusLine().getStatusCode() == HttpStatus.SC_FORBIDDEN ){
                throw new HttpException.OperationFailure("--->>HttpUtil 无法访问esign，未开通白名单"+res.getStatusLine());
            }else if (res.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                throw new HttpException.OperationFailure("--->>HttpUtil HTTP访问异常"+res.getStatusLine());
            }

            //获取请求响应对象和响应entity
            HttpEntity httpEntity = res.getEntity();
            if(httpEntity != null) {
                resCtx = EntityUtils.toString(httpEntity,"utf-8");
                log.info("--->>HttpUtil 获取响应内容：{}",resCtx);
            }

        } catch (IOException e) {
            log.error("--->>HttpUtil HTTP请求失败",e);
        }finally {
            if(res != null) {
                try {
                    res.close();
                } catch (IOException e) {
                    throw new HttpException.OperationFailure("--->>HttpUtil 关闭请求响应失败",e);
                }
            }
        }
        return JSONObject.parseObject(resCtx);
    }

    /**
     * 文件下载
     *
     * @param reqType
     * @param url
     * @param name
     * @return
     */
    public static String downloadFile(ReqType reqType, String url, String name) throws HttpException{
        HttpRequestBase reqBase = reqType.getHttpType(url);
        log.info("\n--->>HttpUtil 开始向地址[{}]发起 [{}] 请求",url,reqBase.getMethod());
        CloseableHttpClient httpClient = getHttpClient();
        //响应对象
        CloseableHttpResponse res = null;
        //执行请求
        try {
            res = httpClient.execute(reqBase);
            if(res.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new HttpException.OperationFailure("--->>HTTP访问异常:"+res.getStatusLine());
            }
            HttpEntity entity = res.getEntity();
            InputStream in = entity.getContent();
            //这里为存储路径
            String path = "usr/local/chat_data";
            File file = new File(path);
            if (file.getParentFile() == null){
                file.getParentFile().mkdirs();
            }
            FileOutputStream out = new FileOutputStream(file);
            int size = -1;
            byte[] buf = new byte[1024];
            while ((size = in.read(buf)) != -1) {
                out.write(buf, 0, size);
            }
            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(res != null) {
                try {
                    res.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取HttpClient
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(connMgr)
                .setRetryHandler(retryHandler)
                .build();
    }

    /**
     * 请求头和超时时间配置
     *
     * @param httpReqBase
     */
    private static void config(HttpRequestBase httpReqBase) {
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(MAX_TIMEOUT)
                .setConnectTimeout(MAX_TIMEOUT)
                .setSocketTimeout(MAX_TIMEOUT)
                .build();
        httpReqBase.setConfig(requestConfig);
    }

    /**
     * 连接池配置
     */
    private static void cfgPoolMgr() {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();

        //连接池管理器
        connMgr = new PoolingHttpClientConnectionManager(registry);
        //最大连接数
        connMgr.setMaxTotal(MAX_TOTAL);
        //每个路由基础的连接数
        connMgr.setDefaultMaxPerRoute(ROUTE_MAX_TOTAL);
    }

    /**
     * 设置重试机制
     */
    private static void cfgRetryHandler() {
        retryHandler = new HttpRequestRetryHandler() {

            @Override
            public boolean retryRequest(IOException e, int excCount, HttpContext ctx) {
                //超过最大重试次数，就放弃
                if(excCount > MAX_RETRY) {
                    return false;
                }
                //服务器丢掉了链接，就重试
                if(e instanceof NoHttpResponseException) {
                    return true;
                }
                //不重试SSL握手异常
                if(e instanceof SSLHandshakeException) {
                    return false;
                }
                //中断
                if(e instanceof InterruptedIOException) {
                    return false;
                }
                //目标服务器不可达
                if(e instanceof UnknownHostException) {
                    return false;
                }
                //连接超时
                if(e instanceof ConnectTimeoutException) {
                    return false;
                }
                //SSL异常
                if(e instanceof SSLException) {
                    return false;
                }

                HttpClientContext clientCtx = HttpClientContext.adapt(ctx);
                HttpRequest req = clientCtx.getRequest();
                //如果是幂等请求，就再次尝试
                if(!(req instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }

                return false;
            }
        };
    }
}
