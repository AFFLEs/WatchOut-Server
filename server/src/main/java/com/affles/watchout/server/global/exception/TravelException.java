package com.affles.watchout.server.global.exception;

import com.affles.watchout.server.global.exception.GeneralException;
import com.affles.watchout.server.global.status.ErrorStatus;

public class TravelException extends GeneralException {
  public TravelException(ErrorStatus status) {
    super(status);
  }
}
