package api.vitaport.health.usermodule.infra.security.services;

import api.vitaport.health.usermodule.domain.models.user.User;
import api.vitaport.health.usermodule.infra.config.UserSecretKeys;
import api.vitaport.health.usermodule.infra.exceptions.CreateTokenException;
import api.vitaport.health.usermodule.infra.exceptions.InvalidTokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private UserSecretKeys userSecretKeys;

    @Autowired
    public TokenService(UserSecretKeys userSecretKeys){
        this.userSecretKeys = userSecretKeys;
    }

    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(userSecretKeys.tokenGenPass);
            return JWT.create()
                    .withIssuer(userSecretKeys.ISSUER)
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.dataExpire())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new CreateTokenException("Error: Failure to create TokenJWT\n" + exception.getMessage());
        }
    }

    public String getSubject(String tokenJWT){
        try{
            Algorithm algorithm = Algorithm.HMAC256(userSecretKeys.tokenGenPass);
            return JWT.require(algorithm)
                    .withIssuer(userSecretKeys.ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new InvalidTokenException("Error: Invalid TokenJWT or Expired TokenJWT\n" + exception.getMessage());
        }
    }

    private Instant dataExpire() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
