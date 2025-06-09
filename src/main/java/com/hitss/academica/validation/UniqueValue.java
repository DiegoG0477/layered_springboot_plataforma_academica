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

    /**
     * Nombre del campo en la base de datos para realizar la consulta.
     */
    String fieldName();

    /**
     * La clase del repositorio que se usará para la consulta.
     */
    Class<?> repository();
}