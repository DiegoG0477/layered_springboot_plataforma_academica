package com.hitss.academica.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.support.Repositories;

import java.lang.reflect.Method;
import java.util.Optional;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    @Autowired
    private ApplicationContext applicationContext;

    private String fieldName;
    private Class<?> repositoryClass;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.repositoryClass = constraintAnnotation.repository();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null || applicationContext == null) {
            return true;
        }

        try {
            Repositories repositories = new Repositories(applicationContext);
            Optional<Object> repositoryOptional = repositories.getRepositoryFor(repositoryClass);

            if (repositoryOptional.isEmpty()) {

                return true;
            }

            Object repository = repositoryOptional.get();

            String methodName = "existsBy" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = repository.getClass().getMethod(methodName, value.getClass());

            boolean exists = (boolean) method.invoke(repository, value);

            return !exists;

        } catch (Exception e) {
            return true;
        }
    }
}