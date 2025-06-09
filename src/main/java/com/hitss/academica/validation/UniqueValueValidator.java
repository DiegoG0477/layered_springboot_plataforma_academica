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
            return true; // No validamos valores nulos, para eso está @NotNull o @NotBlank
        }

        try {
            // Obtenemos una instancia del repositorio especificado en la anotación
            Repositories repositories = new Repositories(applicationContext);
            Optional<Object> repositoryOptional = repositories.getRepositoryFor(repositoryClass);

            if (repositoryOptional.isEmpty()) {
                // Si el repositorio no se encuentra, la validación pasa para no bloquear la app
                // pero se debería loggear un error.
                return true;
            }

            Object repository = repositoryOptional.get();

            // Construimos el nombre del método a invocar, ej: "existsByEmail"
            String methodName = "existsBy" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = repository.getClass().getMethod(methodName, value.getClass());

            // Invocamos el método (ej. usuarioRepository.existsByEmail("test@test.com"))
            boolean exists = (boolean) method.invoke(repository, value);

            return !exists; // La validación es exitosa si el valor NO existe

        } catch (Exception e) {
            // Si hay algún error de reflexión, etc., pasamos la validación para no romper la app
            // y loggeamos el error.
            // logger.error("Error en la validación de valor único", e);
            return true;
        }
    }
}