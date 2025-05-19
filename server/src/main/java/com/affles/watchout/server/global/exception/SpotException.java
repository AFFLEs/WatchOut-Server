package com.affles.watchout.server.global.exception;

import com.affles.watchout.server.global.exception.GeneralException;
import com.affles.watchout.server.global.status.ErrorStatus;

public class SpotException extends GeneralException {
    public SpotException(ErrorStatus status) {
        super(status);
    }
}
