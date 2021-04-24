package com.xu.atchat.advance.assertion;



import com.xu.atchat.advance.exception.BaseException;
import com.xu.atchat.advance.exception.BusinessException;
import com.xu.atchat.advance.enums.IResponseEnum;

import java.text.MessageFormat;

/**
 * <p>业务异常断言</p>
 *
 */
public interface BusinessExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new BusinessException(this, args, msg, t);
    }

}
