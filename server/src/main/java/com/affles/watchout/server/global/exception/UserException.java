package com.affles.watchout.server.global.exception;

import com.affles.watchout.server.global.exception.GeneralException;
import com.affles.watchout.server.global.status.ErrorStatus;

public class UserException extends GeneralException {
    public UserException(ErrorStatus status) {
        super(status);
    }
}
