package api.vitaport.health.usermodule.infra.security.filters;

import api.vitaport.health.commonmodule.infra.exceptions.dto.ErrorDTO;
import api.vitaport.health.usermodule.infra.exceptions.InvalidTokenException;
import api.vitaport.health.usermodule.infra.repositories.IUserRepository;
import api.vitaport.health.usermodule.infra.security.services.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final IUserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthenticationFilter(TokenService tokenService, IUserRepository userRepository, ObjectMapper objectMapper){
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = this.retrieveToken(request);
        try {
            if (tokenJWT != null){
                var subject = tokenService.getSubject(tokenJWT);
                var user = userRepository.findUserByEmail(subject);
                if (user == null) throw new EntityNotFoundException("user not found or not exists");
                var authenticationToken = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request,response);
        } catch (InvalidTokenException e){
            ErrorDTO errorDTO = new ErrorDTO(e);
            sendError(response, errorDTO.code(), errorDTO);
        }
    }

    private void sendError(HttpServletResponse response, int status, ErrorDTO errorDTO) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorDTO));
    }

    private String retrieveToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null){
            return authorizationHeader.replace("Bearer ","");
        }
        return null;
    }
}
