package com.affles.watchout.server.global.exception;

import com.affles.watchout.server.global.status.ErrorStatus;

public class DisasterException extends GeneralException {

    public DisasterException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

    public DisasterException(ErrorStatus errorStatus, String detailMessage) {
        super(errorStatus);
    }
}