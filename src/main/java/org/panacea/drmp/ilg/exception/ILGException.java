package org.panacea.drmp.ilg.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ILGException extends RuntimeException {
    protected Throwable throwable;

    public ILGException(String message) {
        super(message);
    }

    public ILGException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
        log.error("ILGException: ", message);
    }

    public Throwable getCause() {
        return throwable;
    }
}
