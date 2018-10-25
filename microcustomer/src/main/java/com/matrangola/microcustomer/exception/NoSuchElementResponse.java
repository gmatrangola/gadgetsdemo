package com.matrangola.microcustomer.exception;

public class NoSuchElementResponse {
    private final String reason;

    public NoSuchElementResponse(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
