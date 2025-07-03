package api.vitaport.health.usermodule.controllers;



import api.vitaport.health.usermodule.domain.models.user.User;
import api.vitaport.health.usermodule.mappers.UserMapper;
import api.vitaport.health.usermodule.usecases.user.*;
import api.vitaport.health.usermodule.usecases.user.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserMapper userMapper;
    private final RegisterUserUsecase registerUserUsecase;
    private final LoginUserUsecase loginUserUsecase;
    private final GetUserDataUsecase getUserDataUsecase;
    private final GetUsersDataListUsecase getUsersDataListUsecase;
    private final GetUserDataByEmailUsecase getUserDataByEmailUsecase;
    private final GetUsersDataPageUsecase getUsersDataPageUsecase;

    @Autowired
    public UserController(RegisterUserUsecase registerUserUsecase, LoginUserUsecase loginUserUsecase,
                          GetUserDataUsecase getUserDataUsecase, GetUsersDataListUsecase getUsersDataListUsecase,
                          GetUserDataByEmailUsecase getUserDataByEmailUsecase, GetUsersDataPageUsecase getUsersDataPageUsecase,
                          UserMapper userMapper){
        this.userMapper = userMapper;
        this.loginUserUsecase = loginUserUsecase;
        this.registerUserUsecase = registerUserUsecase;
        this.getUserDataUsecase = getUserDataUsecase;
        this.getUsersDataListUsecase = getUsersDataListUsecase;
        this.getUserDataByEmailUsecase = getUserDataByEmailUsecase;
        this.getUsersDataPageUsecase = getUsersDataPageUsecase;
    }

    @PostMapping("/register")
    public ResponseEntity<CreatedUserDTO> registerUser(@RequestBody @Validated RegisterUserDTO registerUserDTO,
                                                       UriComponentsBuilder uriBuilder){
        User user = registerUserUsecase.execute(registerUserDTO);
        CreatedUserDTO createdUserDTO = userMapper.mapToCreatedUserDTO(user);
        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(createdUserDTO.id()).toUri();
        return ResponseEntity.created(uri).body(createdUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> loginUser(@RequestBody @Validated LoginUserDTO loginUserDTO){
        TokenDTO tokenDTO = loginUserUsecase.execute(loginUserDTO);
        return ResponseEntity.ok(tokenDTO);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ReadUserDTO> getUserData(@PathVariable("id") UUID id){
        User user = getUserDataUsecase.execute(id);
        ReadUserDTO readUserDTO = userMapper.mapToReadUserDTO(user);
        return ResponseEntity.ok(readUserDTO);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ReadUserDTO> getUserData(@PathVariable("email") String email){
        User user = getUserDataByEmailUsecase.execute(email);
        ReadUserDTO readUserDTO = userMapper.mapToReadUserDTO(user);
        return ResponseEntity.ok(readUserDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReadUserDTO>> getUsers(){
        List<User> users = getUsersDataListUsecase.execute();
        List<ReadUserDTO> readUserDTOList = userMapper.mapToReadUserDTOList(users);
        return ResponseEntity.ok(readUserDTOList);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ReadUserDTO>> getUsers(Pageable pageable){
        Page<User> users = getUsersDataPageUsecase.execute(pageable);
        Page<ReadUserDTO> readUserDTOPage = userMapper.mapToReadUserDTOPage(users);
        return ResponseEntity.ok(readUserDTOPage);
    }

}
