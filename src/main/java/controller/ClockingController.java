package controller;

import domain.Clocking;
import domain.Contract;
import domain.Timesheet;
import domain.dtos.response.ClockingDTO;
import domain.enums.TimesheetStatus;
import domain.validators.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.*;
import utils.Utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;

@CrossOrigin
@RestController
public class ClockingController {

    private final ContractRepository contractRepository = new ContractRepository();
    private final TimesheetRepository timesheetRepository = new TimesheetRepository();
    private final ClockingRepository clockingRepository = new ClockingRepository();

    @PostMapping("/clocking")
    public ResponseEntity<String> saveClocking(@RequestBody Clocking clocking) throws Validator.ValidationException {
        String idTimesheet = clocking.getUsernameEmployee() + clocking.getFromHour().getYear() +
                clocking.getFromHour().getMonthValue();
        Timesheet timesheet = timesheetRepository.findOne(idTimesheet);
        int numberOfHours = Integer.parseInt(contractRepository.findOne(clocking.getUsernameEmployee()).getType().toString().split("_")[2]);
        if (timesheet == null) {
            int workingDays = Utils.calculateWorkingDays(LocalDate.of(clocking.getFromHour().getYear(),
                    clocking.getFromHour().getMonthValue(), 1));
            Timesheet newTimesheet = new Timesheet();
            newTimesheet.setIdTimesheet(idTimesheet);
            newTimesheet.setUsernameEmployee(clocking.getUsernameEmployee());
            newTimesheet.setYear(clocking.getFromHour().getYear());
            newTimesheet.setMonth(clocking.getFromHour().getMonthValue());
            newTimesheet.setRequiredHours((float)numberOfHours * workingDays);
            newTimesheet.setStatus(TimesheetStatus.OPENED);
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
