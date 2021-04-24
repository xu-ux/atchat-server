package com.xu.atchat.model.vo;

import com.xu.atchat.model.dto.FriendRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author xucl
 * @version 1.0
 * @date 2020/5/4 13:26
 * @description 请求列表展示
 */
@Getter
@Setter
public class FriendRequestVO {

    /**
     * 申请的请求数据
     */
    private List<FriendRequestDTO> applyList;

    /**
     * 已处理过的历史请求数据
     */
    private List<FriendRequestDTO> processedList;
}
