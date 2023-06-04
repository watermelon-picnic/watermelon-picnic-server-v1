package com.server.watermelonserverv1.domain.comment.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class CommentNotFoundException extends WatermelonException {
    public static final CommentNotFoundException EXCEPTION = new CommentNotFoundException();
    public CommentNotFoundException() { super(ErrorCode.COMMENT_NOT_FOUND_EXCEPTION); }
}
