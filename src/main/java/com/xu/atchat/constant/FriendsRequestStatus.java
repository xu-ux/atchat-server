package com.xu.atchat.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/4 12:59
 * @description 好友请求状态
 */
@Getter
@AllArgsConstructor
public enum FriendsRequestStatus implements BaseEnum{
    EXPIRATION(0,"已过期","发出的请求超过处理时间"),
    APPLY(1,"申请中","发出的有效的好友申请请求"),
    REJECT(2,"已拒绝","被添加方拒绝此次好友申请"),
    ACCEPT(3,"已接受","被添加放接受了此次好友申请")
    ;
    private int id;

    private String status;

    private String remark;

    public static FriendsRequestStatus getFriendsRequestStatusById(Integer id){
        Map<Integer, FriendsRequestStatus> map = Arrays.stream(values()).collect(Collectors.toMap(FriendsRequestStatus::getId, Function.identity()));
        FriendsRequestStatus friendsRequestStatus = map.get(id);
        return friendsRequestStatus;
    }

}
