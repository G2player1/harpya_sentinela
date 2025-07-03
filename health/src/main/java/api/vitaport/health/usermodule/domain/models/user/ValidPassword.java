package api.vitaport.health.usermodule.domain.models.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "A senha deve ter no mínimo 8 caracteres, ao menos 1 letra maiúscula e 1 caractere especial.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
