package services;

import domain.Administrator;
import domain.Employee;
import domain.validators.Validator;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.AdministratorRepository;
import repository.EmployeeRepository;

import java.util.List;

@CrossOrigin
@RestController
public class RestServices {
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final AdministratorRepository administratorRepository = new AdministratorRepository();

    @PostMapping("/employee")
    public ResponseEntity<String> saveEmployee(@RequestBody Employee employee) {
        Employee employeeReturned;
        try {
            employeeReturned = employeeRepository.save(employee);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (employeeReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/employee/{usernameEmployee}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String usernameEmployee) {
        Employee employee = employeeRepository.delete(usernameEmployee);
        if (employee == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/employee")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employee) {
        Employee employeeReturned;
        try {
            employeeReturned = employeeRepository.update(employee);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (employeeReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/employee/{usernameEmployee}")
    public ResponseEntity<Employee> findOneEmployee(@PathVariable String usernameEmployee) {
        Employee employee = employeeRepository.findOne(usernameEmployee);
        if (employee != null)
            return new ResponseEntity<>(employee, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> list = employeeRepository.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/administrator")
    public ResponseEntity<String> saveAdministrator(@RequestBody Administrator administrator) {
        Administrator administrator1;
        try {
            administrator1 = administratorRepository.save(administrator);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (administrator1 == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/administrator/{usernameEmployee}")
    public ResponseEntity<String> deleteAdministrator(@PathVariable String usernameEmployee) {
        Administrator administrator = administratorRepository.delete(usernameEmployee);
        if (administrator == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/administrator")
    public ResponseEntity<List<Administrator>> getAdministrators() {
        List<Administrator> list = administratorRepository.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
