package com.xu.atchat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.atchat.constant.FriendSearchStatus;
import com.xu.atchat.model.domain.User;
import com.xu.atchat.model.dto.UserBaseDTO;
import com.xu.atchat.model.vo.UserLoginVO;
import com.xu.atchat.model.vo.UserRegisterVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
public interface IUserService extends IService<User> {

    /**
     * 校验登录
     * @param userLoginVO
     * @return
     */
    UserBaseDTO findOneByUsernameAndPassword(UserLoginVO userLoginVO);

    /**
     * 注册
     * @param userRegisterVO
     * @return
     */
    void saveUserRegister(UserRegisterVO userRegisterVO);

    /**
     * 用户名是否存在
     * @param username
     * @return
     */
    boolean findUsernameIsExist(String username);

    /**
     * 查询好友用户名状态
     * @param myUserId 我的id
     * @param friendUsername 查找的用户名（精确查找）
     * @return
     */
    FriendSearchStatus searchFriends(String myUserId,String friendUsername);

    /**
     * 根据用户名精确搜索用户
     * @param username
     * @return
     */
    User findOneByUsername(String username);



}