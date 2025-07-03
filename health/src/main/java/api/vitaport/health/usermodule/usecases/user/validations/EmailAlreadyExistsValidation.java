package api.vitaport.health.usermodule.usecases.user.validations;

import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.usermodule.infra.repositories.IUserRepository;
import api.vitaport.health.usermodule.infra.exceptions.UserRegisterValidationException;
import api.vitaport.health.usermodule.usecases.user.dtos.RegisterUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailAlreadyExistsValidation implements IRegisterUserValidation{

    private final IUserRepository userRepository;

    @Autowired
    public EmailAlreadyExistsValidation(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void validate(RegisterUserDTO registerUserDTO) {
        if (userRepository.getUserByEmail(registerUserDTO.email()) != null)
            throw new UserRegisterValidationException(400, ErrorEnum.LAPI,"this email is already taken, try another one");
    }
}
