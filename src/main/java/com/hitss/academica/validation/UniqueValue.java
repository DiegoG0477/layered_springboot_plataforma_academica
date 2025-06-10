package com.hitss.academica.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueValueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueValue {

    String message() default "El valor ya existe en la base de datos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String fieldName();

    Class<?> repository();
}