package com.xu.atchat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.atchat.model.domain.MailMsg;
import com.xu.atchat.mapper.MailMsgMapper;
import com.xu.atchat.service.IMailMsgService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邮箱列表信息 服务实现类
 * </p>
 *
 * @author xucl
 * @since 2020-04-17
 */
@Service
public class MailMsgServiceImpl extends ServiceImpl<MailMsgMapper, MailMsg> implements IMailMsgService {

}
