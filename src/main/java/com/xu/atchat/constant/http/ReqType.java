package com.xu.atchat.constant.http;

import org.apache.http.client.methods.*;

/**
 * @Author: xucl
 * @Date: 2020/8/6
 * @Description: <p></p>
 */
public enum ReqType {

    POST{
        @Override
        public HttpRequestBase getHttpType(String url) {
            return new HttpPost(url);
        }
    },
    GET{
        @Override
        public HttpRequestBase getHttpType(String url) {
            return new HttpGet(url);
        }
    },
    DELETE{
        @Override
        public HttpRequestBase getHttpType(String url) {
            return new HttpDelete(url);
        }
    },
    PUT{
        @Override
        public HttpRequestBase getHttpType(String url) {
            return new HttpPut(url);
        }
    },

    ;

    public abstract HttpRequestBase getHttpType(String url);
}
