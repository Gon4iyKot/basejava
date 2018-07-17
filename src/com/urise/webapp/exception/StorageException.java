package com.urise.webapp.exception;

public class StorageException extends RuntimeException {
    public String getUuid() {
        return uuid;
    }

    private final String uuid;

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

}
