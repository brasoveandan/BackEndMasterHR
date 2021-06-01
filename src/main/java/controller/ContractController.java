package controller;

import domain.Contract;
import domain.Employee;
import domain.dtos.response.ContractDTO;
import domain.validators.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ContractRepository;
import repository.EmployeeRepository;
import utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@CrossOrigin
@RestController
public class ContractController {
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final ContractRepository contractRepository = new ContractRepository();

    @PostMapping("/contract")
    public ResponseEntity<String> saveContract(@RequestBody ContractDTO contractDTO) {
        Employee employeeReturned;
        Employee employee = employeeRepository.findOne(contractDTO.getUsername());
        employee.setPersonalNumber(employee.getPersonalNumber());
        employee.setFirstName(contractDTO.getFirstName());
        employee.setLastName(contractDTO.getLastName());
        employee.setMail(contractDTO.getMail());
        employee.setPhoneNumber(contractDTO.getPhoneNumber());
        employee.setSocialSecurityNumber(contractDTO.getSocialSecurityNumber());
        employee.setBirthday(contractDTO.getBirthday());
        employee.setGender(contractDTO.getGender());
        employee.setBankName(contractDTO.getBankName());
        employee.setBankAccountNumber(contractDTO.getBankAccountNumber());
        try {
            employeeReturned = employeeRepository.update(employee);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (employeeReturned == null) {
            Contract contractReturned;
            Contract contract = Utils.contractDTOToContract(contractDTO);
            contract.setEmployee(employee);
            try {
                contractReturned = contractRepository.save(contract);
            } catch (Validator.ValidationException exception) {
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
            }
            if (contractReturned == null)
                return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/contract")
    public ResponseEntity<String> updateContract(@RequestBody ContractDTO contractDTO) {
        Employee employeeReturned;
        Employee employee = Utils.contractDTOToEmployee(contractDTO, employeeRepository.findOne(contractDTO.getUsername()));
        try {
            employeeReturned = employeeRepository.update(employee);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (employeeReturned == null) {
            Contract contractReturned;
            Contract contract = Utils.contractDTOToContract(contractDTO);
            try {
                contractReturned = contractRepository.update(contract);
            } catch (Validator.ValidationException exception) {
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
            }
            if (contractReturned == null)
                return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/contract/{usernameEmployee}")
    public ResponseEntity<ContractDTO> findOneContract(@PathVariable String usernameEmployee) {
        Employee employee = employeeRepository.findOne(usernameEmployee);
        Contract contract = contractRepository.findOne(usernameEmployee);
        if (contract != null) {
            ContractDTO contractDTO = new ContractDTO(employee.getLastName(),
                    employee.getFirstName(),
                    employee.getPersonalNumber(),
                    employee.getMail(),
                    employee.getPhoneNumber(),
                    employee.getSocialSecurityNumber(),
                    contract.getCompanyName(),
                    contract.getBaseSalary(),
                    contract.getCurrency(),
                    contract.getHireDate(),
                    contract.getExpirationDate(),
                    contract.getDepartment(),
                    contract.getPosition(),
                    employee.getBirthday(),
                    employee.getGender(),
                    employee.getBankName(),
                    employee.getBankAccountNumber(),
                    contract.getOvertimeIncreasePercent(),
                    contract.isTaxExempt(),
                    contract.getTicketValue(),
                    contract.getDaysOff());
            contractDTO.setType(Utils.contractTypeToString(contract.getType()));
            return new ResponseEntity<>(contractDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/contract")
    public ResponseEntity<List<ContractDTO>> getContracts() {
        List<ContractDTO> list = new ArrayList<>();
        contractRepository.findAll().forEach(contract -> {
            Employee employee = employeeRepository.findOne(contract.getUsernameEmployee());
            ContractDTO contractDTO = new ContractDTO(employee.getLastName(),
                    employee.getFirstName(),
                    employee.getPersonalNumber(),
                    employee.getMail(),
                    employee.getPhoneNumber(),
                    employee.getSocialSecurityNumber(),
                    contract.getCompanyName(),
                    contract.getBaseSalary(),
                    contract.getCurrency(),
                    contract.getHireDate(),
                    contract.getExpirationDate(),
                    contract.getDepartment(),
                    contract.getPosition(),
                    employee.getBirthday(),
                    employee.getGender(),
                    employee.getBankName(),
                    employee.getBankAccountNumber(),
                    contract.getOvertimeIncreasePercent(),
                    contract.isTaxExempt(),
                    contract.getTicketValue(),
                    contract.getDaysOff());
            contractDTO.setUsername(employee.getUsername());
            contractDTO.setType(Utils.contractTypeToString(contract.getType()));
            list.add(contractDTO);
        });
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        list.sort(Comparator.comparing(ContractDTO::getUsername).thenComparing(ContractDTO::getPersonalNumber));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
