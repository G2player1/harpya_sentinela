package api.vitaport.health.healthmodule.usecases.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record RegisterEmployeeDTO(
        @NotNull
        UUID user_id,
        @NotBlank
        String registrationNumber,
        @NotBlank
        @Pattern(
                regexp = "^(\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2})$",
                message = "CPF inválido. Use apenas números ou o formato 000.000.000-00"
        )
        String cpf,
        @NotBlank
        String fullName,
        @NotBlank
        String socialName
) {
}
