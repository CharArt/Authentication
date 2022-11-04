package com.converter.converter.auth.entity.repository.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Set;

public class CaptchaResponseDTO {
    private boolean success;
    @JsonAlias("error-codes")
    private Set<String> errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Set<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(Set<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}
