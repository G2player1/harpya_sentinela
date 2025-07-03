package api.vitaport.health.usermodule.usecases.user.validations;

import api.vitaport.health.usermodule.usecases.user.dtos.RegisterUserDTO;

public interface IRegisterUserValidation {

    void validate(RegisterUserDTO registerUserDTO);
}
