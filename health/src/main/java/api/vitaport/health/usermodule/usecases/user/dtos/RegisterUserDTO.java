package api.vitaport.health.usermodule.usecases.user.dtos;

import api.vitaport.health.usermodule.domain.models.user.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record RegisterUserDTO(
        @NotBlank
        String name,
        @NotBlank
                @Email
        String email,
        @NotBlank
                @ValidPassword
        String password,
        @NotBlank
        @Pattern(
                regexp = "^(employee|data_analyst|system_manager)$",
                message = "Role must be one of: employee, data_analyst, system_manager"
        )
        String role
) {
}
