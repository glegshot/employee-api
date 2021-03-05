package org.employee.exception.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.employee.exception.ControllerHandlerException;

import java.util.ArrayList;
import java.util.List;


public class ErrorResult {
    private List<FieldValidationError> fieldErrors = new ArrayList<>();

    public ErrorResult() {
    }

    public ErrorResult(String field, String message) {
        this.fieldErrors.add(new FieldValidationError(field, message));
    }


    public List<FieldValidationError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldValidationError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}