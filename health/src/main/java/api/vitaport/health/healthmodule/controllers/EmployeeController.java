package api.vitaport.health.healthmodule.controllers;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.healthmodule.mappers.EmployeeMapper;
import api.vitaport.health.healthmodule.usecases.employee.*;
import api.vitaport.health.healthmodule.usecases.employee.dto.CreatedEmployeeDTO;
import api.vitaport.health.healthmodule.usecases.employee.dto.ReadEmployeeDTO;
import api.vitaport.health.healthmodule.usecases.employee.dto.RegisterEmployeeDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeMapper employeeMapper;
    private final RegisterEmployeeUsecase registerEmployeeUsecase;
    private final GetEmployeeDataUsecase getEmployeeDataUsecase;
    private final GetEmployeeDataListUsecase getEmployeeDataListUsecase;
    private final GetEmployeeDataPageUsecase getEmployeeDataPageUsecase;
    private final GetEmployeeDataByEmailUsecase getEmployeeDataByEmailUsecase;
    private final GetEmployeeDataByRegistrationNumberUsecase getEmployeeDataByRegistrationNumberUsecase;

    @Autowired
    public EmployeeController(EmployeeMapper employeeMapper,
                              RegisterEmployeeUsecase registerEmployeeUsecase,
                              GetEmployeeDataUsecase getEmployeeDataUsecase,
                              GetEmployeeDataListUsecase getEmployeeDataListUsecase,
                              GetEmployeeDataPageUsecase getEmployeeDataPageUsecase,
                              GetEmployeeDataByEmailUsecase getEmployeeDataByEmailUsecase,
                              GetEmployeeDataByRegistrationNumberUsecase getEmployeeDataByRegistrationNumberUsecase){
        this.employeeMapper = employeeMapper;
        this.registerEmployeeUsecase = registerEmployeeUsecase;
        this.getEmployeeDataUsecase = getEmployeeDataUsecase;
        this.getEmployeeDataListUsecase = getEmployeeDataListUsecase;
        this.getEmployeeDataPageUsecase = getEmployeeDataPageUsecase;
        this.getEmployeeDataByEmailUsecase = getEmployeeDataByEmailUsecase;
        this.getEmployeeDataByRegistrationNumberUsecase = getEmployeeDataByRegistrationNumberUsecase;
    }

    @PostMapping("/register")
    public ResponseEntity<CreatedEmployeeDTO> registerEmployee(@RequestBody @Valid RegisterEmployeeDTO registerEmployeeDTO,
                                                               UriComponentsBuilder uriComponentsBuilder){
        Employee employee = registerEmployeeUsecase.execute(registerEmployeeDTO);
        CreatedEmployeeDTO createdEmployeeDTO = employeeMapper.mapToCreatedEmployeeDTO(employee);
        URI uri = uriComponentsBuilder.path("/employee/{employee_id}").buildAndExpand(createdEmployeeDTO.employee_id()).toUri();
        return ResponseEntity.created(uri).body(createdEmployeeDTO);
    }

    @GetMapping("/employee/{employee_id}")
    public ResponseEntity<ReadEmployeeDTO> getEmployeeData(@PathVariable("employee_id") UUID id){
        Employee employee = getEmployeeDataUsecase.execute(id);
        ReadEmployeeDTO readEmployeeDTO = employeeMapper.mapToReadEmployeeDTO(employee);
        return ResponseEntity.ok(readEmployeeDTO);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ReadEmployeeDTO> getEmployeeDataByEmail(@PathVariable("email") String email){
        Employee employee = getEmployeeDataByEmailUsecase.execute(email);
        ReadEmployeeDTO readEmployeeDTO = employeeMapper.mapToReadEmployeeDTO(employee);
        return ResponseEntity.ok(readEmployeeDTO);
    }

    @GetMapping("/registration/{registration_number}")
    public ResponseEntity<ReadEmployeeDTO> getEmployeeDataByRegistrationNumber(@PathVariable("registration_number") String reg){
        Employee employee = getEmployeeDataByRegistrationNumberUsecase.execute(reg);
        ReadEmployeeDTO readEmployeeDTO = employeeMapper.mapToReadEmployeeDTO(employee);
        return ResponseEntity.ok(readEmployeeDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReadEmployeeDTO>> getEmployees(){
        List<Employee> employees = getEmployeeDataListUsecase.execute();
        List<ReadEmployeeDTO> readEmployeeDTOList = employeeMapper.mapToReadEmployeeDTOList(employees);
        return ResponseEntity.ok(readEmployeeDTOList);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ReadEmployeeDTO>> getEmployees(Pageable pageable){
        Page<Employee> employees = getEmployeeDataPageUsecase.execute(pageable);
        Page<ReadEmployeeDTO> readEmployeeDTOPage = employeeMapper.mapToReadEmployeeDTOPage(employees);
        return ResponseEntity.ok(readEmployeeDTOPage);
    }
}
