package controller;

import domain.*;
import domain.dtos.request.AuthenticationRequest;
import domain.dtos.request.RequestDTO;
import domain.dtos.request.RequestHolidayDTO;
import domain.dtos.request.ResetPassword;
import domain.dtos.response.*;
import domain.enums.AdminRole;
import domain.enums.HolidayType;
import domain.enums.RequestStatus;
import domain.enums.TimesheetStatus;
import domain.validators.ResetPasswordValidator;
import domain.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import repository.*;
import controller.springSecurity.JwtUtil;
import controller.springSecurity.MyUserDetailsService;
import utils.Utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static utils.Utils.stringToHolidayType;
import static utils.Utils.stringToRequestStatus;

@CrossOrigin
@RestController
public class MasterHRController {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil = new JwtUtil();
    private final MyUserDetailsService userDetailsService = new MyUserDetailsService();

    private static final Set<HolidayType> OTHER_TYPES = Collections.unmodifiableSet(EnumSet.of(HolidayType.BLOOD_DONATION, HolidayType.MARRIAGE, HolidayType.FUNERAL));
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final ContractRepository contractRepository = new ContractRepository();
    private final PayslipRepository payslipRepository = new PayslipRepository();
    private final HolidayRepository holidayRepository = new HolidayRepository();
    private final RequestRepository requestRepository = new RequestRepository();
    private final TimesheetRepository timesheetRepository = new TimesheetRepository();
    private final ClockingRepository clockingRepository = new ClockingRepository();

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        Employee employee = employeeRepository.findOne(authenticationRequest.getUsername());
        authenticationResponse.setAdminRole(employee.getAdminRole().toString());
        authenticationResponse.setName(employee.getFirstName());
        authenticationResponse.setJwt(jwt);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody String recipientEmail, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        Employee employee = employeeRepository.findOneByEmail(recipientEmail);
        if (employee != null) {
            final UserDetails userDetails = userDetailsService.loadUserByEmail(employee.getUsername());
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            String link = request.getHeader("Origin") + "/reset_password?token= " + jwt;
            String fullName = employee.getLastName() + " " + employee.getFirstName();

            helper.setFrom("masterHR.contact@gmail.com", "MasterHR Support");
            helper.setTo(recipientEmail);

            String subject = "Resetare parola MasterHR";
            String content = "<p>Salut, " + fullName + "</p>"
                    + "<p>Pentru ati reseta parola, acceseaza link-ul de mai jos.</p>"
                    + "<p><a href=\"" + link + "\">Schimba parola</a></p>"
                    + "<br>"
                    + "<p>Daca nu ai initiat tu aceasta cerere, te rugam sa ignori acest email.";

            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPassword resetPassword) throws Validator.ValidationException {
        ResetPasswordValidator validator = new ResetPasswordValidator();
        try {
            validator.validate(resetPassword);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        String usernameEmployee = jwtTokenUtil.extractUsername(resetPassword.getToken());
        Employee employee = employeeRepository.findOne(usernameEmployee);
        if (employee != null) {
            employee.setPassword(resetPassword.getPassword());
            if (employeeRepository.update(employee) == null)
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    

    //EmployeeController
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

    @PutMapping("/employeeAccount")
    public ResponseEntity<String> updateEmployeeAccount(@RequestBody Employee employee) {
        Employee employeeReturned;
        Employee employeeToAdd = employeeRepository.findOne(employee.getUsername());
        employeeToAdd.setPassword(employee.getPassword());
        employeeToAdd.setPersonalNumber(employee.getPersonalNumber());
        employeeToAdd.setAdminRole(employee.getAdminRole());
        employeeToAdd.setMail(employee.getMail());
        try {
            employeeReturned = employeeRepository.update(employeeToAdd);
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
        List<Employee> list = new ArrayList<>();
        employeeRepository.findAll().forEach(employee -> {
            if (employee.getAdminRole() != AdminRole.ADMIN)
                list.add(employee);
        });
        list.sort(Comparator.comparing(Employee::getUsername));
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    
    //ContractController
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

    
    //PayslipController
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
            float overtimeHours = payslip.getWorkedHours() + payslip.getHomeOfficeHours() - payslip.getRequiredHours();
            if (overtimeHours > 0)
                payslipDTO.setOvertimeHours(overtimeHours);

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
    
    
    //HolidayController
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

    @GetMapping("/summaryHoliday/{usernameEmployee}")
    public ResponseEntity<SummaryHolidayDTO> getSummaryHoliday(@PathVariable String usernameEmployee) {
        SummaryHolidayDTO summaryHolidayDTO = new SummaryHolidayDTO();
        AtomicInteger daysTaken = new AtomicInteger();
        AtomicInteger medicalLeave = new AtomicInteger();
        AtomicInteger otherLeave = new AtomicInteger();
        AtomicInteger overtimeLeave = new AtomicInteger();
        holidayRepository.findAll().forEach(holiday -> {
            if (holiday.getUsernameEmployee().equals(usernameEmployee) && requestRepository.findOne(String.valueOf(holiday.getRequest().getIdRequest())).getStatus() == RequestStatus.ACCEPTED)  {
                HolidayDTO holidayDTO = new HolidayDTO();
                holidayDTO.setFromDate(holiday.getFromDate());
                holidayDTO.setToDate(holiday.getToDate());
                holidayDTO.setNumberOfDays((int) DAYS.between(holiday.getFromDate(), holiday.getToDate()) + 1);
                if (holiday.getType() == HolidayType.MEDICAL) {
                    medicalLeave.addAndGet(holidayDTO.getNumberOfDays());
                }
                if (OTHER_TYPES.contains(holiday.getType()))
                    otherLeave.addAndGet(holidayDTO.getNumberOfDays());
                if (holiday.getType() == HolidayType.OVERTIME_LEAVE)
                    overtimeLeave.addAndGet(holiday.getOvertimeLeave());
                daysTaken.addAndGet(holidayDTO.getNumberOfDays());
            }
        });
        summaryHolidayDTO.setMedicalLeave(medicalLeave.get());
        summaryHolidayDTO.setDaysTaken(daysTaken.get());
        summaryHolidayDTO.setOtherLeave(otherLeave.get());
        summaryHolidayDTO.setOvertimeLeave(overtimeLeave.get());
        summaryHolidayDTO.setDaysAvailable((contractRepository.findOne(usernameEmployee) == null) ? 0 : contractRepository.findOne(usernameEmployee).getDaysOff());
        return new ResponseEntity<>(summaryHolidayDTO, HttpStatus.OK);
    }

    @GetMapping("/holiday/{usernameEmployee}")
    public ResponseEntity<List<HolidayDTO>> getHolidays(@PathVariable String usernameEmployee) {
        List<HolidayDTO> holidayDTOList = new ArrayList<>();
        holidayRepository.findAll().forEach(holiday -> {
            if (holiday.getUsernameEmployee().equals(usernameEmployee) ) {
                HolidayDTO holidayDTO = new HolidayDTO();
                holidayDTO.setIdRequest(holiday.getRequest().getIdRequest());
                holidayDTO.setUser(holiday.getUsernameEmployee());
                holidayDTO.setFromDate(holiday.getFromDate());
                holidayDTO.setToDate(holiday.getToDate());
                holidayDTO.setNumberOfDays((int) DAYS.between(holiday.getFromDate(), holiday.getToDate()) + 1);
                holidayDTO.setProxyUsername(holiday.getProxyUsername());
                holidayDTO.setType(Utils.holidayTypeToString(holiday.getType()));
                holidayDTO.setStatus(Utils.requestStatusToString(requestRepository.
                        findOne(String.valueOf(holiday.getRequest().getIdRequest())).getStatus()));
                if (holiday.getType() == HolidayType.MEDICAL) {
                    holidayDTO.setStatus("ACCEPTATĂ");
                }
                holidayDTOList.add(holidayDTO);
            }
        });
        if (holidayDTOList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        holidayDTOList.sort(Comparator.comparing(HolidayDTO::getFromDate).reversed());
        return new ResponseEntity<>(holidayDTOList, HttpStatus.OK);
    }

    @GetMapping("/holidayRequest/{usernameEmployee}")
    public ResponseEntity<List<HolidayDTO>> getAllHolidayRequests(@PathVariable String usernameEmployee) {
        List<HolidayDTO> holidayDTOList = new ArrayList<>();
        String department = contractRepository.findOne(usernameEmployee) == null ? "" : contractRepository.findOne(usernameEmployee).getDepartment();
        holidayRepository.findAll().forEach(holiday -> {
            if (contractRepository.findOne(holiday.getUsernameEmployee()).getDepartment().equals(department)) {
                HolidayDTO holidayDTO = new HolidayDTO();
                holidayDTO.setIdRequest(holiday.getRequest().getIdRequest());
                holidayDTO.setUser(holiday.getUsernameEmployee());
                holidayDTO.setFromDate(holiday.getFromDate());
                holidayDTO.setToDate(holiday.getToDate());
                holidayDTO.setNumberOfDays((int) DAYS.between(holiday.getFromDate(), holiday.getToDate()));
                holidayDTO.setProxyUsername(holiday.getProxyUsername());
                holidayDTO.setType(Utils.holidayTypeToString(holiday.getType()));
                holidayDTO.setStatus(Utils.requestStatusToString(requestRepository.
                        findOne(String.valueOf(holiday.getRequest().getIdRequest())).getStatus()));
                if (holiday.getType() == HolidayType.MEDICAL) {
                    holidayDTO.setStatus("ACCEPTATĂ");
                }
                holidayDTOList.add(holidayDTO);
            }
        });
        if (holidayDTOList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        holidayDTOList.sort(Comparator.comparing(HolidayDTO::getFromDate).reversed().thenComparing(HolidayDTO::getStatus));
        return new ResponseEntity<>(holidayDTOList, HttpStatus.OK);
    }

    @GetMapping("/holiday/{usernameEmployee}/{year}/{month}")
    public ResponseEntity<List<HolidayDTO>> getHolidayPerMonth(@PathVariable String usernameEmployee, @PathVariable int year, @PathVariable int month) {
        List<HolidayDTO> holidayDTOList = new ArrayList<>();
        holidayRepository.findAll().forEach(holiday -> {
            if (holiday.getUsernameEmployee().equals(usernameEmployee) && holiday.getFromDate().getYear() == year
                    && holiday.getFromDate().getMonthValue() == month &&
                    requestRepository.findOne(String.valueOf(holiday.getRequest().getIdRequest())).getStatus() == RequestStatus.PENDING) {
                HolidayDTO holidayDTO = new HolidayDTO();
                holidayDTO.setIdRequest(holiday.getRequest().getIdRequest());
                holidayDTO.setUser(holiday.getUsernameEmployee());
                holidayDTO.setFromDate(holiday.getFromDate());
                holidayDTO.setToDate(holiday.getToDate());
                holidayDTO.setNumberOfDays((int) DAYS.between(holiday.getFromDate(), holiday.getToDate()));
                holidayDTO.setProxyUsername(holiday.getProxyUsername());
                holidayDTO.setType(Utils.holidayTypeToString(holiday.getType()));
                holidayDTO.setStatus(Utils.requestStatusToString(requestRepository.
                        findOne(String.valueOf(holiday.getRequest().getIdRequest())).getStatus()));
                if (holiday.getType() == HolidayType.MEDICAL) {
                    holidayDTO.setStatus("ACCEPTATĂ");
                }
                holidayDTOList.add(holidayDTO);
            }
        });
        if (holidayDTOList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(holidayDTOList, HttpStatus.OK);
    }

    
    //RequestController
    @PostMapping("/request")
    public ResponseEntity<String> saveRequest(@RequestBody RequestHolidayDTO request) throws Validator.ValidationException {
        String idTimesheet = request.getUsernameEmployee() + request.getFromDate().getYear() + request.getFromDate().getMonthValue();
        Timesheet timesheet = timesheetRepository.findOne(idTimesheet);
        int numberOfHours = Integer.parseInt(contractRepository.findOne(request.getUsernameEmployee()).getType().toString().split("_")[2]);
        if (timesheet == null) {
            String idTimesheetOld = request.getUsernameEmployee() + request.getFromDate().getYear() + (request.getFromDate().getMonthValue() - 1);
            Timesheet oldTimesheet = timesheetRepository.findOne(idTimesheetOld);
            int workingDays = Utils.calculateWorkingDays(LocalDate.of(request.getFromDate().getYear(),
                    request.getFromDate().getMonthValue(), 1));
            Timesheet newTimesheet = new Timesheet();
            newTimesheet.setIdTimesheet(idTimesheet);
            newTimesheet.setUsernameEmployee(request.getUsernameEmployee());
            newTimesheet.setYear(request.getFromDate().getYear());
            newTimesheet.setMonth(request.getFromDate().getMonthValue());
            newTimesheet.setRequiredHours((float)numberOfHours * workingDays);
            newTimesheet.setStatus(TimesheetStatus.OPENED);
            newTimesheet.setTotalOvertimeHours(oldTimesheet.getTotalOvertimeHours());
            timesheet = newTimesheet;
            timesheetRepository.save(newTimesheet);
        }
        Holiday holidayReturned;
        Holiday holiday = new Holiday();
        holiday.setIdHoliday(request.getUsernameEmployee() + request.getFromDate() + request.getToDate());
        holiday.setUsernameEmployee(request.getUsernameEmployee());
        holiday.setFromDate(request.getFromDate());
        holiday.setToDate(request.getToDate());
        holiday.setProxyUsername(request.getProxyUsername());
        holiday.setType(stringToHolidayType(request.getType()));
        if (holiday.getType() == HolidayType.OVERTIME_LEAVE) {
            int numberOfDays = (int) DAYS.between(holiday.getFromDate(), holiday.getToDate());
            int requiredOvertimeLeave = numberOfDays * numberOfHours;

            if (requiredOvertimeLeave <= timesheet.getOvertimeHours() + timesheet.getTotalOvertimeHours()) {
                holiday.setOvertimeLeave(requiredOvertimeLeave);
                if (timesheet.getOvertimeHours() >= requiredOvertimeLeave) {
                    timesheet.setOvertimeHours(timesheet.getOvertimeHours() - requiredOvertimeLeave);
                }
                else {
                    requiredOvertimeLeave -= timesheet.getOvertimeHours();
                    timesheet.setOvertimeHours(0);
                    timesheet.setTotalOvertimeHours(timesheet.getTotalOvertimeHours() - requiredOvertimeLeave);
                }
            }
            else return new ResponseEntity<>("Nu sunt suficiente ore suplimentare.", HttpStatus.EXPECTATION_FAILED);
        }
        try {
            holidayReturned = holidayRepository.save(holiday);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (holidayReturned == null) {
            Request requestReturned;
            Request requestToSave = new Request(request.getUsernameEmployee(), request.getDescription(),
                    stringToRequestStatus(request.getStatus()), request.getSubmittedDate(), idTimesheet);
            requestToSave.setIdRequest(0);
            try {
                requestReturned = requestRepository.save(requestToSave);
            } catch (Validator.ValidationException exception) {
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
            }
            holiday.setRequest(requestReturned);
            try {
                holidayRepository.update(holiday);
                timesheetRepository.update(timesheet);
            } catch (Validator.ValidationException exception) {
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
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
        RequestStatus requestStatus = request.getStatus();
        request = requestRepository.findOne(String.valueOf(request.getIdRequest()));
        request.setStatus(requestStatus);
        try {
            requestReturned = requestRepository.update(request);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (requestReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/request/{usernameEmployee}")
    public ResponseEntity<List<RequestDTO>> getRequests(@PathVariable String usernameEmployee) {
        List<Request> list = requestRepository.findAll();
        List<RequestDTO> requestDTOList = new ArrayList<>();
        list.forEach(request -> {
            if (request.getUsernameEmployee().equals(usernameEmployee)) {
                RequestDTO requestDTO = new RequestDTO();
                requestDTO.setDescription(request.getDescription());
                requestDTO.setStatus(Utils.requestStatusToString(request.getStatus()));
                requestDTO.setSubmittedDate(request.getSubmittedDate());
                holidayRepository.findAll().forEach(holiday -> {
                    if (holiday.getRequest() == request) {
                        requestDTO.setType(Utils.holidayTypeToString(holiday.getType()));
                    }
                });
                requestDTOList.add(requestDTO);
            }
        });
        if (requestDTOList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        requestDTOList.sort(Comparator.comparing(RequestDTO::getSubmittedDate).reversed());
        return new ResponseEntity<>(requestDTOList, HttpStatus.OK);
    }


    //TimesheetController
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
    public ResponseEntity<String> updateTimesheet(@RequestBody TimesheetDTO timesheetDTO) throws Validator.ValidationException {
        Timesheet timesheetReturned;
        String timesheetID = timesheetDTO.getUsernameEmployee() + timesheetDTO.getYear() + timesheetDTO.getMonth();
        Timesheet timesheet = timesheetRepository.findOne(timesheetID);
        timesheet.setStatus(timesheetDTO.getStatus());
        timesheetDTO.setTotalOvertimeHours(timesheetDTO.getTotalOvertimeHours() + timesheetDTO.getOvertimeHours());
        try {
            timesheetReturned = timesheetRepository.update(timesheet);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (timesheetReturned == null) {
            Contract contract = contractRepository.findOne(timesheet.getUsernameEmployee());
            Payslip payslip = new Payslip();
            payslip.setIdPayslip(timesheet.getIdTimesheet());
            payslip.setUsernameEmployee(timesheet.getUsernameEmployee());
            payslip.setYear(timesheet.getYear());
            payslip.setMonth(timesheet.getMonth());
            payslip.setWorkedHours(timesheet.getWorkedHours());
            payslip.setHomeOfficeHours(timesheet.getHomeOfficeHours());
            payslip.setRequiredHours(timesheet.getRequiredHours());
            payslip.setIncreases(0);
            float overtime = timesheet.getTotalOvertimeHours() + timesheet.getOvertimeHours();
            if (payslip.getMonth() == 12 && overtime != 0) {
                int overtimePercent = contract.getOvertimeIncreasePercent();
                float overtimeIncrease = overtimePercent * overtime / 100;
                payslip.setOvertimeIncreases(overtimeIncrease);
            }
            float ticketValue = contract.getTicketValue();
            AtomicInteger workDays = new AtomicInteger();
            clockingRepository.findAll().forEach(clocking -> {
                if (clocking.getUsernameEmployee().equals(timesheetDTO.getUsernameEmployee()) &&
                        clocking.getFromHour().getMonthValue() == payslip.getMonth())
                    workDays.getAndIncrement();
            });
            payslip.setTicketsValue(ticketValue * workDays.get());
            float percent = (payslip.getWorkedHours() + payslip.getHomeOfficeHours()) / payslip.getRequiredHours();
            if (percent < 1) {
                float salary = contract.getBaseSalary() * percent;
                payslip.setGrossSalary(salary);
            }
            else
                payslip.setGrossSalary(contract.getBaseSalary());
            if (contract.isTaxExempt())
                payslip.setNetSalary(payslip.getGrossSalary());
            else payslip.setNetSalary(payslip.getGrossSalary() * 55 / 100);
            payslipRepository.save(payslip);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/timesheet/{idTimesheet}")
    public ResponseEntity<TimesheetDTO> findOneTimesheet(@PathVariable String idTimesheet) {
        TimesheetDTO timesheetDTO = new TimesheetDTO();
        Timesheet timesheet = timesheetRepository.findOne(idTimesheet);
        if (timesheet != null) {
            timesheetDTO.setYear(timesheet.getYear());
            timesheetDTO.setMonth(timesheet.getMonth());
            timesheetDTO.setWorkedHours(timesheet.getWorkedHours());
            timesheetDTO.setHomeOfficeHours(timesheet.getHomeOfficeHours());
            timesheetDTO.setRequiredHours(timesheet.getRequiredHours());
            timesheetDTO.setOvertimeHours(timesheet.getOvertimeHours());
            timesheetDTO.setTotalOvertimeHours(timesheet.getTotalOvertimeHours());
            return new ResponseEntity<>(timesheetDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/timesheet")
    public ResponseEntity<List<TimesheetDTO>> getTimesheet() {
        List<TimesheetDTO> list = new ArrayList<>();
        timesheetRepository.findAll().forEach(timesheet -> {
            TimesheetDTO timesheetDTO = new TimesheetDTO();
            timesheetDTO.setUsernameEmployee(timesheet.getUsernameEmployee());
            timesheetDTO.setPersonalNumber(employeeRepository.findOne(timesheet.getUsernameEmployee()).getPersonalNumber());
            timesheetDTO.setDepartment(contractRepository.findOne(timesheet.getUsernameEmployee()).getDepartment());
            timesheetDTO.setYear(timesheet.getYear());
            timesheetDTO.setMonth(timesheet.getMonth());
            timesheetDTO.setWorkedHours(timesheet.getWorkedHours());
            timesheetDTO.setHomeOfficeHours(timesheet.getHomeOfficeHours());
            timesheetDTO.setRequiredHours(timesheet.getRequiredHours());
            timesheetDTO.setOvertimeHours(timesheet.getOvertimeHours());
            timesheetDTO.setTotalOvertimeHours(timesheet.getTotalOvertimeHours());
            timesheetDTO.setStatus(timesheet.getStatus());
            list.add(timesheetDTO);
        });
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    //ClockingController
    @PostMapping("/clocking")
    public ResponseEntity<String> saveClocking(@RequestBody Clocking clocking) throws Validator.ValidationException {
        String idTimesheet = clocking.getUsernameEmployee() + clocking.getFromHour().getYear() +
                clocking.getFromHour().getMonthValue();
        Timesheet timesheet = timesheetRepository.findOne(idTimesheet);
        int numberOfHours = Integer.parseInt(contractRepository.findOne(clocking.getUsernameEmployee()).getType().toString().split("_")[2]);
        if (timesheet == null) {
            String idTimesheetOld = clocking.getUsernameEmployee() + clocking.getFromHour().getYear() + (clocking.getFromHour().getMonthValue() - 1);
            Timesheet oldTimesheet = timesheetRepository.findOne(idTimesheetOld);
            int workingDays = Utils.calculateWorkingDays(LocalDate.of(clocking.getFromHour().getYear(),
                    clocking.getFromHour().getMonthValue(), 1));
            Timesheet newTimesheet = new Timesheet();
            newTimesheet.setIdTimesheet(idTimesheet);
            newTimesheet.setUsernameEmployee(clocking.getUsernameEmployee());
            newTimesheet.setYear(clocking.getFromHour().getYear());
            newTimesheet.setMonth(clocking.getFromHour().getMonthValue());
            newTimesheet.setRequiredHours((float)numberOfHours * workingDays);
            newTimesheet.setStatus(TimesheetStatus.OPENED);
            if (oldTimesheet != null) {
                newTimesheet.setTotalOvertimeHours(oldTimesheet.getTotalOvertimeHours());
            }
            timesheet = newTimesheet;
            timesheetRepository.save(newTimesheet);
        }
        Clocking clockingReturned;
        clocking.setIdClocking(clocking.getUsernameEmployee() + clocking.getFromHour().getMonthValue() + clocking.getFromHour().getDayOfMonth());
        clocking.setIdTimesheet(timesheet.getIdTimesheet());
        try {
            clockingReturned = clockingRepository.save(clocking);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (clockingReturned == null && clocking.getFromHour() != clocking.getToHour()) {
            long clockingHours = HOURS.between(clocking.getFromHour(), clocking.getToHour());
            if (clocking.getType() != null && !clocking.getType().equals("Normal"))
                timesheet.setHomeOfficeHours(clockingHours + timesheet.getHomeOfficeHours());
            else
                timesheet.setWorkedHours(clockingHours + timesheet.getWorkedHours());
            if (numberOfHours <= clockingHours)
                timesheet.setOvertimeHours((float)clockingHours - numberOfHours);
            try {
                timesheetRepository.update(timesheet);
            } catch (Validator.ValidationException exception) {
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
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
        clocking.setUsernameEmployee(clocking.getUsernameEmployee());
        clocking.setIdClocking(clocking.getUsernameEmployee()+clocking.getToHour().getMonthValue()+clocking.getToHour().getDayOfMonth());

        try {
            clockingReturned = clockingRepository.update(clocking);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (clockingReturned == null) {
            String idTimesheet = clocking.getUsernameEmployee() + clocking.getFromHour().getYear() +
                    clocking.getFromHour().getMonthValue();
            Timesheet timesheet = timesheetRepository.findOne(idTimesheet);
            int numberOfHours = Integer.parseInt(contractRepository.findOne(clocking.getUsernameEmployee()).getType().toString().split("_")[2]);
            long clockingHours = HOURS.between(clocking.getFromHour(), clocking.getToHour());
            if (clocking.getType() != null && !clocking.getType().equals("Normal"))
                timesheet.setHomeOfficeHours(clockingHours + timesheet.getHomeOfficeHours());
            timesheet.setWorkedHours(clockingHours + timesheet.getWorkedHours());
            if (numberOfHours <= clockingHours)
                timesheet.setOvertimeHours((float)clockingHours - numberOfHours);
            try {
                timesheetRepository.update(timesheet);
            } catch (Validator.ValidationException exception) {
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/clocking/{usernameEmployee}/{year}/{month}")
    public ResponseEntity<List<ClockingDTO>> getAllClocking(@PathVariable String usernameEmployee, @PathVariable int year, @PathVariable int month) {
        List<ClockingDTO> clockingDTOList = new ArrayList<>();
        String idTimesheet = usernameEmployee + year + month;
        clockingRepository.findAll().forEach(clocking -> {
            if (clocking.getUsernameEmployee().equals(usernameEmployee) && clocking.getIdTimesheet().equals(idTimesheet)) {
                ClockingDTO clockingDTO = new ClockingDTO();
                clockingDTO.setDay(clocking.getFromHour().getDayOfMonth());

                String format = "HH:mm";
                clockingDTO.setFromHour(clocking.getFromHour().toLocalTime().format(DateTimeFormatter.ofPattern(format)));
                clockingDTO.setToHour(clocking.getToHour().toLocalTime().format(DateTimeFormatter.ofPattern(format)));
                LocalTime workedHours =  clocking.getToHour().toLocalTime().minusNanos(clocking.getFromHour().
                        toLocalTime().toNanoOfDay());
                clockingDTO.setWorkedHours(workedHours.format(DateTimeFormatter.ofPattern(format)));
                Contract contract = contractRepository.findOne(usernameEmployee);
                int hoursPerDay = Integer.parseInt(contract.getType().toString().split("_")[2]);
                if (workedHours.isAfter(LocalTime.of(hoursPerDay,0)))
                    clockingDTO.setOvertimeHours(workedHours.minusNanos(LocalTime.of(hoursPerDay,0).
                            toNanoOfDay()).format(DateTimeFormatter.ofPattern(format)));
                clockingDTOList.add(clockingDTO);
            }
        });
        if (clockingDTOList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        clockingDTOList.sort(Comparator.comparing(ClockingDTO::getDay).reversed());
        return new ResponseEntity<>(clockingDTOList, HttpStatus.OK);
    }
}
