package com.ef.common;

/**
 * For any anomaly found during the execution of the code
 */
public class ParserException extends RuntimeException {

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Object... params) {
        super(String.format(message, params));
    }
}
