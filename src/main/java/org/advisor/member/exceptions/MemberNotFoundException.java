package org.advisor.member.exceptions;

import org.advisor.global.exceptions.scripts.AlertBackException;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends AlertBackException {
    public MemberNotFoundException() {
        super("NotFound.member", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
