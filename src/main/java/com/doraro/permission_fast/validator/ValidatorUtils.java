package com.doraro.permission_fast.validator;

import com.doraro.permission_fast.exception.ApiGlobalException;
import com.doraro.permission_fast.exception.CodeMsg;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static void validate(Object object, Class<?>... groups)
            throws ApiGlobalException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for(ConstraintViolation<Object> constraint:  constraintViolations){
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new ApiGlobalException(CodeMsg.BIND_ERROR.fillArgs(msg.toString()));
        }
    }
}
