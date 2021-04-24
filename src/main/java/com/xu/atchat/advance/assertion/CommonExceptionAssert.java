package com.xu.atchat.advance.assertion;



import com.xu.atchat.advance.exception.ArgumentException;
import com.xu.atchat.advance.exception.BaseException;
import com.xu.atchat.advance.enums.IResponseEnum;

import java.text.MessageFormat;

/**
 * <pre>
 *
 * </pre>
 *
 */
public interface CommonExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new ArgumentException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new ArgumentException(this, args, msg, t);
    }

}
