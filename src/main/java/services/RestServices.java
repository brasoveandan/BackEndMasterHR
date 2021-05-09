package services;

import domain.*;
import domain.dtos.request.AddEmployeeDTO;
import domain.dtos.request.RequestDTO;
import domain.dtos.request.RequestHolidayDTO;
import domain.dtos.response.*;
import domain.enums.ContractType;
import domain.enums.HolidayType;
import domain.enums.RequestStatus;
import domain.validators.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.*;
import utils.Utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.temporal.ChronoUnit.*;
import static utils.Utils.*;

@CrossOrigin
@RestController
public class RestServices {
    private static final Set<HolidayType> OTHER_TYPES = Collections.unmodifiableSet(EnumSet.of(HolidayType.BLOOD_DONATION, HolidayType.MARRIAGE, HolidayType.FUNERAL));
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final ContractRepository contractRepository = new ContractRepository();
    private final RequestRepository requestRepository = new RequestRepository();
    private final PayslipRepository payslipRepository = new PayslipRepository();
    private final HolidayRepository holidayRepository = new HolidayRepository();
    private final TimesheetRepository timesheetRepository = new TimesheetRepository();
    private final ClockingRepository clockingRepository = new ClockingRepository();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Employee employee){
        Employee employeeStored = employeeRepository.findOne(employee.getUsername());
        if (employeeStored != null) {
            if (employee.getPassword().equals(employeeStored.getPassword())) {
                if (employeeStored.getAdminRole() == null)
                    return new ResponseEntity<>(new ResponseDTO("null", employeeStored.getFirstName()),HttpStatus.OK);
                else
                    return new ResponseEntity<>(new ResponseDTO(employeeStored.getAdminRole().toString(), employeeStored.getFirstName()),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<ContractDTO> findOneContract(@PathVariable String usernameEmployee) {
        Employee employee = employeeRepository.findOne(usernameEmployee);
        Contract contract = contractRepository.findOne(usernameEmployee);
        ContractDTO contractDTO = new ContractDTO();
        if (contract != null) {
            contractDTO.setLastName(employee.getLastName());
            contractDTO.setFirstName(employee.getFirstName());
            contractDTO.setPersonalNumber(employee.getPersonalNumber());
            contractDTO.setMail(employee.getMail());
            contractDTO.setPhoneNumber(employee.getPhoneNumber());
            contractDTO.setSocialSecurityNumber(employee.getSocialSecurityNumber());
            contractDTO.setBirthday(employee.getBirthday());
            contractDTO.setGender(employee.getGender());
            contractDTO.setBankName(employee.getBankName());
            contractDTO.setBankAccountNumber(employee.getBankAccountNumber());
            contractDTO.setCompanyName(contract.getCompanyName());
            contractDTO.setDepartment(contract.getDepartment());
            contractDTO.setPosition(contract.getPosition());
            contractDTO.setBaseSalary(contract.getBaseSalary());
            contractDTO.setHireDate(contract.getHireDate());
            contractDTO.setTaxExempt(contract.isTaxExempt());
            contractDTO.setOvertimeIncreasePercent(contract.getOvertimeIncreasePercent());
            contractDTO.setExpirationDate(contract.getExpirationDate());
            if (contract.getType() == ContractType.FULL_TIME_8)
                contractDTO.setType("Permanent");
            if (contract.getType() == ContractType.PART_TIME_6)
                contractDTO.setType("Student 6 ore");
            if (contract.getType() == ContractType.PART_TIME_4)
                contractDTO.setType("Student 4 ore");
            return new ResponseEntity<>(contractDTO, HttpStatus.OK);
        }
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
    public ResponseEntity<String> saveRequest(@RequestBody RequestHolidayDTO request) {
        String idTimesheet = request.getUsernameEmployee() + request.getFromDate().getYear() + request.getFromDate().getMonthValue();
        Timesheet timesheet = timesheetRepository.findOne(idTimesheet);
        Holiday holidayReturned;
        Holiday holiday = new Holiday();
        holiday.setIdRequest(0);
        holiday.setIdHoliday(request.getUsernameEmployee() + request.getFromDate() + request.getToDate());
        holiday.setUsernameEmployee(request.getUsernameEmployee());
        holiday.setFromDate(request.getFromDate());
        holiday.setToDate(request.getToDate());
        holiday.setType(stringToHolidayType(request.getType()));
        if (holiday.getType() == HolidayType.OVERTIME_LEAVE) {
            Contract contract = contractRepository.findOne(holiday.getUsernameEmployee());
            int numberOfHours = Integer.parseInt(contract.getType().toString().split("_")[2]);
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
        holiday.setProxyUsername(request.getProxyUsername());
        try {
            holidayReturned = holidayRepository.save(holiday);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (holidayReturned == null) {
            Request requestReturned;
            Request requestToSave = new Request(request.getUsernameEmployee(), request.getDescription(),
                    stringToRequestStatus(request.getStatus()), request.getSubmittedDate(), idTimesheet);
            try {
                requestReturned = requestRepository.save(requestToSave);
            } catch (Validator.ValidationException exception) {
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
            }
            holiday.setIdRequest(requestReturned.getIdRequest());
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
                    if (holiday.getIdRequest() != null && holiday.getIdRequest().equals(request.getIdRequest())) {
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



    //PayslipServices
    @PostMapping("/payslip")
    public ResponseEntity<String> savePayslip(@RequestBody Payslip payslip) {
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

        Employee employee = employeeRepository.findOne(payslip.getUsernameEmployee());
        payslipDTO.setFirstName(employee.getFirstName());
        payslipDTO.setLastName(employee.getLastName());
        payslipDTO.setPersonalNumber(employee.getPersonalNumber());


        //calculate taxes
        float total = payslip.getGrossSalary() + payslip.getTicketsValue();
        payslipDTO.setCAS(25 *  total / 100);
        payslipDTO.setCASS(10 * total / 100);
        payslipDTO.setIV(10 *  total / 100);

        if (payslip != null)
            return new ResponseEntity<>(payslipDTO, HttpStatus.OK);
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

    @GetMapping("/summaryHoliday/{usernameEmployee}")
    public ResponseEntity<SummaryHolidayDTO> getSummaryHoliday(@PathVariable String usernameEmployee) {
        SummaryHolidayDTO summaryHolidayDTO = new SummaryHolidayDTO();
        AtomicInteger daysTaken = new AtomicInteger();
        AtomicInteger medicalLeave = new AtomicInteger();
        AtomicInteger otherLeave = new AtomicInteger();
        AtomicInteger overtimeLeave = new AtomicInteger();
        holidayRepository.findAll().forEach(holiday -> {
            if (holiday.getUsernameEmployee().equals(usernameEmployee) && requestRepository.findOne(String.valueOf(holiday.getIdRequest())).getStatus() != RequestStatus.PENDING)  {
                HolidayDTO holidayDTO = new HolidayDTO();
                holidayDTO.setFromDate(holiday.getFromDate());
                holidayDTO.setToDate(holiday.getToDate());
                holidayDTO.setNumberOfDays((int) DAYS.between(holiday.getFromDate(), holiday.getToDate()));
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
        summaryHolidayDTO.setDaysAvailable(contractRepository.findOne(usernameEmployee).getDaysOff());
        return new ResponseEntity<>(summaryHolidayDTO, HttpStatus.OK);
    }

    @GetMapping("/holiday/{usernameEmployee}")
    public ResponseEntity<List<HolidayDTO>> getHolidays(@PathVariable String usernameEmployee) {
        List<HolidayDTO> holidayDTOList = new ArrayList<>();
        holidayRepository.findAll().forEach(holiday -> {
            if (holiday.getUsernameEmployee().equals(usernameEmployee) ) {
                HolidayDTO holidayDTO = new HolidayDTO();
                holidayDTO.setFromDate(holiday.getFromDate());
                holidayDTO.setToDate(holiday.getToDate());
                holidayDTO.setNumberOfDays((int) DAYS.between(holiday.getFromDate(), holiday.getToDate()));
                holidayDTO.setProxyUsername(holiday.getProxyUsername());
                holidayDTO.setType(Utils.holidayTypeToString(holiday.getType()));
                if (holiday.getIdRequest() != null)
                    holidayDTO.setStatus(Utils.requestStatusToString(requestRepository.
                            findOne(String.valueOf(holiday.getIdRequest())).getStatus()));
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
    public ResponseEntity<TimesheetDTO> findOneTimesheet(@PathVariable String idTimesheet) {
        TimesheetDTO timesheetDTO = new TimesheetDTO();
        Timesheet timesheet = timesheetRepository.findOne(idTimesheet);
        timesheetDTO.setYear(timesheet.getYear());
        timesheetDTO.setMonth(timesheet.getMonth());
        timesheetDTO.setWorkedHours(timesheet.getWorkedHours());
        timesheetDTO.setHomeOfficeHours(timesheet.getHomeOfficeHours());
        timesheetDTO.setRequiredHours(timesheet.getRequiredHours());
        timesheetDTO.setOvertimeHours(timesheet.getOvertimeHours());
        timesheetDTO.setTotalOvertimeLeave(timesheet.getTotalOvertimeHours());
        if (timesheet != null)
            return new ResponseEntity<>(timesheetDTO, HttpStatus.OK);
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
        clocking.setIdTimesheet(clocking.getUsernameEmployee()+clocking.getFromHour().getYear()+clocking.getFromHour().getMonthValue());
        try {
            clockingReturned = clockingRepository.save(clocking);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (clockingReturned == null) {
            Timesheet timesheet = timesheetRepository.findOne(clocking.getUsernameEmployee() +
                    clocking.getFromHour().getYear() + clocking.getFromHour().getMonthValue());
            int numberOfHours = Integer.parseInt(contractRepository.findOne(clocking.getUsernameEmployee()).getType().toString().split("_")[2]);
            long clockingHours = HOURS.between(clocking.getFromHour(), clocking.getToHour());
            if (!clocking.getType().equals("Normal"))
                timesheet.setHomeOfficeHours(clockingHours + timesheet.getHomeOfficeHours());
            timesheet.setWorkedHours(clockingHours + timesheet.getWorkedHours());
            if (numberOfHours <= clockingHours)
                timesheet.setOvertimeHours(clockingHours - numberOfHours);
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
        clocking.setIdClocking(clocking.getUsernameEmployee()+clocking.getToHour().getMonthValue()+clocking.getToHour().getDayOfMonth());
        try {
            clockingReturned = clockingRepository.update(clocking);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (clockingReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/clocking/{usernameEmployee}")
    public ResponseEntity<List<ClockingDTO>> getAllClocking(@PathVariable String usernameEmployee) {
        List<ClockingDTO> clockingDTOList = new ArrayList<>();
        clockingRepository.findAll().forEach(clocking -> {
            if (clocking.getUsernameEmployee().equals(usernameEmployee)) {
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
