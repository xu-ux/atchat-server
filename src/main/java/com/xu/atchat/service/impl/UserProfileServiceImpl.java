package com.xu.atchat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.atchat.model.domain.UserProfile;
import com.xu.atchat.mapper.UserProfileMapper;
import com.xu.atchat.service.IUserProfileService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户详细信息（不经常变动和查询的数据） 服务实现类
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile> implements IUserProfileService {

}
