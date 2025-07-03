package api.vitaport.health.usermodule.infra.security.filters;

import api.vitaport.health.usermodule.infra.repositories.IUserRepository;
import api.vitaport.health.usermodule.infra.security.services.TokenService;
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

    private TokenService tokenService;
    private IUserRepository userRepository;

//    private static final List<String> PUBLIC_ENDPOINTS = Arrays.asList(
//            "/user/register",
//            "/user/login",
//            // URLs do Swagger/OpenAPI
//            "/v3/api-docs",                     // Base para a documentação (essencial para o startswith)
//            "/swagger-ui.html",                 // Página principal do Swagger UI
//            "/swagger-ui/",                     // Recursos estáticos do Swagger UI (CSS, JS, etc.)
//            "/webjars/",                        // Recursos de webjars (onde o Swagger UI busca muitas dependências)
//            "/swagger-resources/",              // Recursos do Swagger
//            "/swagger-resources/configuration/ui", // Configurações de UI
//            "/swagger-resources/configuration/security" // Configurações de segurança
//            // Se você tiver outros endpoints específicos como /v3/api-docs.yaml, etc., adicione-os
//            // mas o "/v3/api-docs" com startsWith geralmente cobre tudo para a documentação base.
//    );


    @Autowired
    public AuthenticationFilter(TokenService tokenService, IUserRepository userRepository){
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String requestUri = request.getRequestURI();
//
//        for (String publicEndpoint : PUBLIC_ENDPOINTS) {
//            if (requestUri.startsWith(publicEndpoint)) {
//                filterChain.doFilter(request, response); // Passa para o próximo filtro e ignora a lógica de token
//                return;
//            }
//        }

        String tokenJWT = this.retrieveToken(request);
        if (tokenJWT != null){
            var subject = tokenService.getSubject(tokenJWT);
            var user = userRepository.findUserByEmail(subject);
            if (user == null) throw new EntityNotFoundException("user not found or not exists");
            var authenticationToken = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response);
    }

    private String retrieveToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null){
            return authorizationHeader.replace("Bearer ","");
        }
        return null;
    }
}
