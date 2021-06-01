package controller;

import domain.Contract;
import domain.Employee;
import domain.Payslip;
import domain.dtos.response.PayslipDTO;
import domain.validators.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.*;

import java.util.Comparator;
import java.util.List;

@CrossOrigin
@RestController
public class PayslipController {
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final ContractRepository contractRepository = new ContractRepository();
    private final PayslipRepository payslipRepository = new PayslipRepository();

    @PostMapping("/payslip")
    public ResponseEntity<String> savePayslip(@RequestBody Payslip payslip)  {
        Payslip payslipReturned;
        payslip.setIdPayslip(payslip.getUsernameEmployee() + payslip.getYear() + payslip.getMonth());
        try {
            payslipReturned = payslipRepository.save(payslip);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (payslipReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("payslip/{idPayslip}")
    public ResponseEntity<String> deletePayslip(@PathVariable String idPayslip) {
        Payslip payslip = payslipRepository.delete(idPayslip);
        if (payslip == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/payslip")
    public ResponseEntity<String> updatePayslip(@RequestBody Payslip payslip) {
        Payslip payslipReturned;
        try {
            payslipReturned = payslipRepository.update(payslip);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (payslipReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/payslip/{idPayslip}")
    public ResponseEntity<PayslipDTO> findOnePayslip(@PathVariable String idPayslip) {
        PayslipDTO payslipDTO = new PayslipDTO();
        Payslip payslip = payslipRepository.findOne(idPayslip);
        if (payslip != null) {
            payslipDTO.setYear(payslip.getYear());
            payslipDTO.setMonth(payslip.getMonth());
            payslipDTO.setIdPayslip(payslip.getIdPayslip());
            payslipDTO.setGrossSalary(payslip.getGrossSalary());
            payslipDTO.setWorkedHours(payslip.getWorkedHours());
            payslipDTO.setHomeOfficeHours(payslip.getHomeOfficeHours());
            payslipDTO.setRequiredHours(payslip.getRequiredHours());
            payslipDTO.setNetSalary(payslip.getNetSalary());
            payslipDTO.setIncreases(payslip.getIncreases());
            payslipDTO.setOvertimeIncreases(payslip.getOvertimeIncreases());
            payslipDTO.setTicketsValue(payslip.getTicketsValue());
            payslipDTO.setOvertimeHours(payslip.getWorkedHours() + payslip.getHomeOfficeHours() - payslip.getRequiredHours());

            Contract contract = contractRepository.findOne(payslip.getUsernameEmployee());
            payslipDTO.setCompanyName(contract.getCompanyName());
            payslipDTO.setDepartment(contract.getDepartment());
            payslipDTO.setPosition(contract.getPosition());
            payslipDTO.setTaxExempt(contract.isTaxExempt());
            payslipDTO.setBaseSalary(contract.getBaseSalary());
            payslipDTO.setCurrency(contract.getCurrency());

            Employee employee = employeeRepository.findOne(payslip.getUsernameEmployee());
            payslipDTO.setFirstName(employee.getFirstName());
            payslipDTO.setLastName(employee.getLastName());
            payslipDTO.setPersonalNumber(employee.getPersonalNumber());

            //calculate taxes
            float total = payslip.getGrossSalary() + payslip.getTicketsValue();
            payslipDTO.setCAS(25 *  total / 100);
            payslipDTO.setCASS(10 * total / 100);
            payslipDTO.setIV(10 *  total / 100);
            return new ResponseEntity<>(payslipDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/payslip")
    public ResponseEntity<List<Payslip>> getPayslips() {
        List<Payslip> list = payslipRepository.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        list.sort(Comparator.comparing(Payslip::getIdPayslip).reversed());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
