package api.vitaport.health.usermodule.usecases.user;

import api.vitaport.health.usermodule.domain.models.user.User;
import api.vitaport.health.usermodule.infra.security.services.TokenService;
import api.vitaport.health.usermodule.usecases.user.dtos.LoginUserDTO;
import api.vitaport.health.usermodule.usecases.user.dtos.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginUserUsecase {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public LoginUserUsecase(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public TokenDTO execute(LoginUserDTO loginUserDTO) {
        var userPassToken = new UsernamePasswordAuthenticationToken(loginUserDTO.email(),loginUserDTO.password());
        var auth = authenticationManager.authenticate(userPassToken);
        var tokenJWT = tokenService.generateToken((User) auth.getPrincipal());
        return new TokenDTO(tokenJWT);
    }
}
