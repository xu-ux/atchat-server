package com.xu.atchat.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/23 18:23
 * @description 线程共享缓存
 */
@Component
public class CacheFactory <K,V> implements Cache <K,V>{

	private  volatile ConcurrentHashMap<K, V> commons = new ConcurrentHashMap<K,V>();

	@SuppressWarnings("unchecked")
	@Override
	public V getCache(K k) {
		// TODO Auto-generated method stub
		return   (V) commons.get(k);
	}

	@Override
	public  void addCache(K k,V v) {
		// TODO Auto-generated method stub
		commons.put(k, v);
	}
	@Override
	public  void putIfAbsentCache(K k,V v) {
		// TODO Auto-generated method stub
		commons.putIfAbsent(k, v);
	}
	@Override
	public  void remove(K k) {
		// TODO Auto-generated method stub
		commons.remove(k);
	}


	
}
