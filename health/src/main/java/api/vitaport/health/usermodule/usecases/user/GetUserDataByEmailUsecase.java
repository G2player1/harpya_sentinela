package api.vitaport.health.usermodule.usecases.user;

import api.vitaport.health.usermodule.domain.models.user.User;
import api.vitaport.health.usermodule.infra.repositories.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUserDataByEmailUsecase {

    public IUserRepository userRepository;

    @Autowired
    public GetUserDataByEmailUsecase(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User execute(String email){
        try {
            return userRepository.getUserByEmail(email);
        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }
    }
}
