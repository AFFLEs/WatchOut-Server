package com.affles.watchout.server.global.exception;

import com.affles.watchout.server.global.status.ErrorStatus;
import lombok.Getter;

@Getter
public class EmergencyException extends GeneralException {
    public EmergencyException(ErrorStatus errorStatus) { super(errorStatus); }
}