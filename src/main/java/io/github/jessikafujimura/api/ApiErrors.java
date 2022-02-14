package io.github.jessikafujimura.api;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    @Getter
    private List<String> errors;

    public ApiErrors(String messagemErro){
        this.errors = Arrays.asList(messagemErro);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }
}
