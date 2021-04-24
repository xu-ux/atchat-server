package com.xu.atchat.service.impl;

import com.xu.atchat.model.domain.Feedback;
import com.xu.atchat.mapper.FeedbackMapper;
import com.xu.atchat.service.IFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xucl
 * @since 2020-09-26
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

}
