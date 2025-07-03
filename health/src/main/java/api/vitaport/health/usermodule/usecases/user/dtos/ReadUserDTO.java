package api.vitaport.health.usermodule.usecases.user.dtos;

import api.vitaport.health.usermodule.domain.models.user.User;
import api.vitaport.health.usermodule.domain.models.user.UserRole;

import java.util.UUID;

public record ReadUserDTO(
        UUID id,
        String name,
        String email,
        UserRole userRole,
        Boolean active
) {
    public ReadUserDTO(User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getUserRole(), user.getActive());
    }
}
