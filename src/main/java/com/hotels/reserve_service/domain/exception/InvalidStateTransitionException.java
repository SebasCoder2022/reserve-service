package com.hotels.reserve_service.domain.exception;

public class InvalidStateTransitionException  extends RuntimeException {
    public InvalidStateTransitionException(String message) {
        super(message);
    }
}
