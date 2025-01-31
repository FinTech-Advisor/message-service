package org.advisor.message.exceptions;

import org.advisor.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class MessageNotFoundException extends CommonException {
    public MessageNotFoundException() {
        super("NotFound.message", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
