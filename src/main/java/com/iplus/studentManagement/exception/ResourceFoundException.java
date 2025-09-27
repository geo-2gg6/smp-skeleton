package com.iplus.studentManagement.exception;

// Custom exception for cases when a resource already exists
public class ResourceFoundException extends RuntimeException {

    public ResourceFoundException() {
        super();
    }

    public ResourceFoundException(String message) {
        super(message);
    }

    public ResourceFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceFoundException(Throwable cause) {
        super(cause);
    }
}
