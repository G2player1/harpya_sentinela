package api.vitaport.health.usermodule.usecases.user.dtos;


import api.vitaport.health.usermodule.domain.models.user.User;

import java.util.UUID;

public record CreatedUserDTO(
        UUID id,
        String name,
        String email
) {
    public CreatedUserDTO(User user){
        this(user.getId(), user.getName(), user.getEmail());
    }
}
