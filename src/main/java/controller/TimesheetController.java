package controller;

import domain.Timesheet;
import domain.dtos.response.TimesheetDTO;
import domain.validators.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class TimesheetController {
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final ContractRepository contractRepository = new ContractRepository();
    private final TimesheetRepository timesheetRepository = new TimesheetRepository();

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
    public ResponseEntity<String> updateTimesheet(@RequestBody TimesheetDTO timesheetDTO) {
        Timesheet timesheetReturned;
        String timesheetID = timesheetDTO.getUsernameEmployee() + timesheetDTO.getYear() + timesheetDTO.getMonth();
        Timesheet timesheet = timesheetRepository.findOne(timesheetID);
        timesheet.setStatus(timesheetDTO.getStatus());
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
}
