package cn.tuyucheng.taketoday.validations.functional.validators;

import cn.tuyucheng.taketoday.validations.functional.model.CustomRequestEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CustomRequestEntityValidator implements Validator {
    public static final int MINIMUM_CODE_LENGTH = 6;

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return CustomRequestEntityValidator.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "field.required");
        CustomRequestEntity request = (CustomRequestEntity) target;
        if (request.getCode() != null && request.getCode().trim().length() < MINIMUM_CODE_LENGTH)
            errors.rejectValue("code", "field.min.length", new Object[]{MINIMUM_CODE_LENGTH}, "The code must be at least [" + MINIMUM_CODE_LENGTH + "] characters in length.");
    }
}