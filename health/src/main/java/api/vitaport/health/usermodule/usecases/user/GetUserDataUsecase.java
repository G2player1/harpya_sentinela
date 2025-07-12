package api.vitaport.health.usermodule.usecases.user;

import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.usermodule.domain.models.user.User;
import api.vitaport.health.usermodule.infra.repositories.IUserRepository;
import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import api.vitaport.health.usermodule.usecases.user.dtos.ReadUserDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserDataUsecase {

    private final IUserRepository userRepository;

    @Autowired
    public GetUserDataUsecase(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User execute(UUID id){
        try {
            return userRepository.getReferenceById(id);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ, "cant get user by employee_id");
        }
    }
}
