package services;

import domain.*;
import domain.validators.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.*;

import java.util.List;

@CrossOrigin
@RestController
public class RestServices {
    private final AdministratorRepository administratorRepository = new AdministratorRepository();
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final ContractRepository contractRepository = new ContractRepository();
    private final RequestRepository requestRepository = new RequestRepository();
    private final PayslipRepository payslipRepository = new PayslipRepository();
    private final HolidayRepository holidayRepository = new HolidayRepository();
    private final TimesheetRepository timesheetRepository = new TimesheetRepository();
    private final ClockingRepository clockingRepository = new ClockingRepository();

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Employee employee){
        Employee employeeStored = employeeRepository.findOne(employee.getUsername());
        if (employeeStored != null) {
            if (employee.getPassword().equals(employeeStored.getPassword())) {
                for (Administrator administrator : administratorRepository.findAll()) {
                    if (administrator.getUsernameEmployee().equals(employee.getUsername()))
                        return new ResponseEntity<>(administrator.getUsernameEmployee() + " " + administrator.getAdminRole(), HttpStatus.OK);
                }
                return new ResponseEntity<>(employee.getFirstName(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //AdministratorServices
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

    //EmployeeServices
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

    //ContractServices
    @PostMapping("/contract")
    public ResponseEntity<String> saveContract(@RequestBody Contract contract) {
        Contract contract1;
        try {
            contract1 = contractRepository.save(contract);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (contract1 == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("contract/{usernameEmployee}")
    public ResponseEntity<String> deleteContract(@PathVariable String usernameEmployee) {
        Contract contract = contractRepository.delete(usernameEmployee);
        if (contract == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/contract")
    public ResponseEntity<String> updateContract(@RequestBody Contract contract) {
        Contract contractReturned;
        try {
            contractReturned = contractRepository.update(contract);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (contractReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/contract/{usernameEmployee}")
    public ResponseEntity<Contract> findOneContract(@PathVariable String usernameEmployee) {
        Contract contract = contractRepository.findOne(usernameEmployee);
        if (contract != null)
            return new ResponseEntity<>(contract, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/contract")
    public ResponseEntity<List<Contract>> getContracts() {
        List<Contract> list = contractRepository.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //RequestServices
    @PostMapping("/request")
    public ResponseEntity<String> saveRequest(@RequestBody Request request) {
        Request requestReturned;
        try {
            requestReturned = requestRepository.save(request);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (requestReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/request/{idRequest}")
    public ResponseEntity<String> deleteRequest(@PathVariable String idRequest) {
        Request request = requestRepository.delete(idRequest);
        if (request == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/request")
    public ResponseEntity<String> updateRequest(@RequestBody Request request) {
        Request requestReturned;
        try {
            requestReturned = requestRepository.update(request);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (requestReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/request/{idRequest}")
    public ResponseEntity<Request> findOneRequest(@PathVariable String idRequest) {
        Request request = requestRepository.findOne(idRequest);
        if (request != null)
            return new ResponseEntity<>(request, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/request")
    public ResponseEntity<List<Request>> getRequests() {
        List<Request> list = requestRepository.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }



    //PayslipServices
    @PostMapping("/payslip")
    public ResponseEntity<String> savePayslip(@RequestBody Payslip payslip) {
        Payslip payslipReturned;
        payslip.setIdPayslip(payslip.getUsernameEmployee()+payslip.getYear() + payslip.getMonth());
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

    @GetMapping("/payslip/{usernameEmployee}")
    public ResponseEntity<Payslip> findOnePayslip(@PathVariable String usernameEmployee) {
        Payslip payslip = payslipRepository.findOne(usernameEmployee);
        if (payslip != null)
            return new ResponseEntity<>(payslip, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/payslip")
    public ResponseEntity<List<Payslip>> getPayslips() {
        List<Payslip> list = payslipRepository.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //HolidayServices
    @PostMapping("/holiday")
    public ResponseEntity<String> saveHoliday(@RequestBody Holiday holiday) {
        Holiday holidayReturned;
        holiday.setIdHoliday(holiday.getUsernameEmployee()+holiday.getFromDate()+holiday.getToDate());
        try {
            holidayReturned = holidayRepository.save(holiday);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (holidayReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/holiday/{idHoliday}")
    public ResponseEntity<String> deleteHoliday(@PathVariable String idHoliday) {
        Holiday holiday = holidayRepository.delete(idHoliday);
        if (holiday == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/holiday")
    public ResponseEntity<String> updateHoliday(@RequestBody Holiday holiday) {
        Holiday holidayReturned;
        try {
            holidayReturned = holidayRepository.update(holiday);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (holidayReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/holiday/{idHoliday}")
    public ResponseEntity<Holiday> findOneHoliday(@PathVariable String idHoliday) {
        Holiday holiday = holidayRepository.findOne(idHoliday);
        if (holiday != null)
            return new ResponseEntity<>(holiday, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/holiday")
    public ResponseEntity<List<Holiday>> getHolidays() {
        List<Holiday> list = holidayRepository.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //TimesheetServices
    @PostMapping("/timesheet")
    public ResponseEntity<String> saveTimesheet(@RequestBody Timesheet timesheet) {
        Timesheet timesheetReturned;
        timesheet.setIdTimesheet(timesheet.getUsernameEmployee() + timesheet.getYear() + timesheet.getMonth());
        try {
            timesheetReturned = timesheetRepository.save(timesheet);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (timesheetReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/timesheet/{idTimesheet}")
    public ResponseEntity<String> deleteTimesheet(@PathVariable String idTimesheet) {
        Timesheet timesheet = timesheetRepository.delete(idTimesheet);
        if (timesheet == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/timesheet")
    public ResponseEntity<String> updateTimesheet(@RequestBody Timesheet timesheet) {
        Timesheet timesheetReturned;
        try {
            timesheetReturned = timesheetRepository.update(timesheet);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (timesheetReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/timesheet/{idTimesheet}")
    public ResponseEntity<Timesheet> findOneTimesheet(@PathVariable String idTimesheet) {
        Timesheet timesheet = timesheetRepository.findOne(idTimesheet);
        if (timesheet != null)
            return new ResponseEntity<>(timesheet, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/timesheet")
    public ResponseEntity<List<Timesheet>> getTimesheet() {
        List<Timesheet> list = timesheetRepository.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //ClockingServices
    @PostMapping("/clocking")
    public ResponseEntity<String> saveClocking(@RequestBody Clocking clocking) {
        Clocking clockingReturned;
        clocking.setIdClocking(clocking.getUsernameEmployee()+clocking.getFromHour().getMonthValue()+clocking.getFromHour().getDayOfMonth());
        try {
            clockingReturned = clockingRepository.save(clocking);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (clockingReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/clocking/{idClocking}")
    public ResponseEntity<String> deleteClocking(@PathVariable String idClocking) {
        Clocking clocking = clockingRepository.delete(idClocking);
        if (clocking == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/clocking")
    public ResponseEntity<String> updateClocking(@RequestBody Clocking clocking) {
        Clocking clockingReturned;
        try {
            clockingReturned = clockingRepository.update(clocking);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (clockingReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/clocking/{idClocking}")
    public ResponseEntity<Clocking> findOneClocking(@PathVariable String idClocking) {
        Clocking clocking = clockingRepository.findOne(idClocking);
        if (clocking != null)
            return new ResponseEntity<>(clocking, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/clocking")
    public ResponseEntity<List<Clocking>> getAllClocking() {
        List<Clocking> list = clockingRepository.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
