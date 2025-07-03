package api.vitaport.health.usermodule.domain.models.user;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword,String> {

    @Override
    public boolean isValid(String senha, ConstraintValidatorContext context) {
        if (senha == null) return false;

        boolean temTamanhoMinimo = senha.length() >= 8;
        boolean temMaiuscula = senha.matches(".*[A-Z].*");
        boolean temEspecial = senha.matches(".*[^a-zA-Z0-9\\s].*");

        return temTamanhoMinimo && temMaiuscula && temEspecial;
    }
}
