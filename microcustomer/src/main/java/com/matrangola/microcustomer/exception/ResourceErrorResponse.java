package com.matrangola.microcustomer.exception;

public class ResourceErrorResponse {
    private final String reason;
    private String className;
    private Long id;

    public ResourceErrorResponse(String reason, String className, Long id) {
        this.reason = reason;
        this.className = className;
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public String getClassName() {
        return className;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ResourceErrorResponse{" +
                "reason='" + reason + '\'' +
                ", className='" + className + '\'' +
                ", id=" + id +
                '}';
    }
}
