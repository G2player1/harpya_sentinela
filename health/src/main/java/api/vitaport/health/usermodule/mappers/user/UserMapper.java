package api.vitaport.health.usermodule.mappers.user;

import api.vitaport.health.usermodule.domain.models.user.User;
import api.vitaport.health.usermodule.domain.models.user.UserRole;
import api.vitaport.health.usermodule.usecases.user.dtos.CreatedUserDTO;
import api.vitaport.health.usermodule.usecases.user.dtos.ReadUserDTO;
import api.vitaport.health.usermodule.usecases.user.dtos.RegisterUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapper {

    public User mapToUserEntity(RegisterUserDTO registerUserDTO){
        return new User(registerUserDTO.name(),
                registerUserDTO.email(), registerUserDTO.password(),
                UserRole.fromString(registerUserDTO.role()));
    }

    public CreatedUserDTO mapToCreatedUserDTO(User user){
        return new CreatedUserDTO(user);
    }

    public ReadUserDTO mapToReadUserDTO(User user){
        return new ReadUserDTO(user);
    }

    public List<ReadUserDTO> mapToReadUserDTOList(List<User> users){
        return users.stream().map(ReadUserDTO::new).toList();
    }

    public Page<ReadUserDTO> mapToReadUserDTOPage(Page<User> users){
        return users.map(ReadUserDTO::new);
    }
}
