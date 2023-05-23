package com.server.watermelonserverv1.domain.post.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class PostIdNotFoundException extends WatermelonException {
    public static final PostIdNotFoundException EXCEPTION = new PostIdNotFoundException();
    private PostIdNotFoundException() { super(ErrorCode.POST_NOT_FOUND); }
}
