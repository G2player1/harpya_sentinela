package api.vitaport.health.usermodule.usecases.user;

import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.usermodule.domain.models.user.User;
import api.vitaport.health.usermodule.infra.repositories.IUserRepository;
import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetUsersDataPageUsecase {

    private final IUserRepository userRepository;

    @Autowired
    public GetUsersDataPageUsecase(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Page<User> execute(Pageable pageable){
        try {
            return userRepository.findAll(pageable);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ, "cant get page of users");
        }
    }
}
