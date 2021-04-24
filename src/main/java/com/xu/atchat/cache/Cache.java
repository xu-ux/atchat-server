package com.xu.atchat.cache;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/23 18:23
 * @description 缓存
 */
public interface Cache <K,V> {
		 V getCache(K k);
		 void addCache(K k, V v);
		 void putIfAbsentCache(K k, V v);
		 void remove(K k);
}
