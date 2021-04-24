package com.xu.atchat.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/23 21:25
 * @description 集合均分工具
 */
public class ListAverageUtils {

    /**
     * 集合按自定义数均分
     *
     * @param source
     * @param n 均分数
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n; //(先计算出余数)
        int number=source.size()/n; //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }


    /**
     * 集合按numbers分成多份 默认999
     *
     * @param source
     * @param <T>
     * @return
     */
    public static  <T> List<List<T>> thousandAssign(List<T> source,Integer numbers){
        int size = (numbers == null || numbers >= 1000) ? 999 : numbers;
        List<List<T>> result=new ArrayList<List<T>>();
        int page = source.size() / size;
        page = page == 0 ? page : page +1;

        if (page == 0){
            result.add(source);
            return result;
        }

        //分集合,少于size不进入
        for (int i = 0 ; i < page ;i++){
            //最后一页
            if (i == (page-1)){
                List<T> ts = source.subList(i*size,source.size());
                result.add(ts);
            }else{
                //取0到size-1
                List<T> ts = source.subList(i*size, (i + 1) * size);
                result.add(ts);
            }
        }
        return result;
    }

}
