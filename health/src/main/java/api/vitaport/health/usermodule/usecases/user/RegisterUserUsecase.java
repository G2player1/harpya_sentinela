package api.vitaport.health.usermodule.usecases.user;

import api.vitaport.health.usermodule.domain.models.user.User;
import api.vitaport.health.usermodule.infra.repositories.IUserRepository;
import api.vitaport.health.usermodule.mappers.UserMapper;
import api.vitaport.health.usermodule.usecases.user.dtos.RegisterUserDTO;
import api.vitaport.health.usermodule.usecases.user.validations.IRegisterUserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterUserUsecase {

    private final UserMapper userMapper;
    private final IUserRepository userRepository;
    private final List<IRegisterUserValidation> registerUserValidations;

    @Autowired
    public RegisterUserUsecase(UserMapper userMapper,IUserRepository userRepository, List<IRegisterUserValidation> registerUserValidations){
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.registerUserValidations = registerUserValidations;
    }

    public User execute(RegisterUserDTO registerUserDTO) {
        registerUserValidations.forEach(validation -> validation.validate(registerUserDTO));
        User user = userMapper.mapToUserEntity(registerUserDTO);
        return userRepository.save(user);
    }
}
