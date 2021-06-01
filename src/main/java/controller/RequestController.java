package controller;

import domain.Holiday;
import domain.Request;
import domain.Timesheet;
import domain.dtos.request.RequestDTO;
import domain.dtos.request.RequestHolidayDTO;
import domain.enums.HolidayType;
import domain.enums.RequestStatus;
import domain.enums.TimesheetStatus;
import domain.validators.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.*;
import utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static utils.Utils.stringToHolidayType;
import static utils.Utils.stringToRequestStatus;

@CrossOrigin
@RestController
public class RequestController {
    private final ContractRepository contractRepository = new ContractRepository();
    private final RequestRepository requestRepository = new RequestRepository();
    private final HolidayRepository holidayRepository = new HolidayRepository();
    private final TimesheetRepository timesheetRepository = new TimesheetRepository();

    @PostMapping("/request")
    public ResponseEntity<String> saveRequest(@RequestBody RequestHolidayDTO request) throws Validator.ValidationException {
        String idTimesheet = request.getUsernameEmployee() + request.getFromDate().getYear() + request.getFromDate().getMonthValue();
        Timesheet timesheet = timesheetRepository.findOne(idTimesheet);
        int numberOfHours = Integer.parseInt(contractRepository.findOne(request.getUsernameEmployee()).getType().toString().split("_")[2]);
        if (timesheet == null) {
            int workingDays = Utils.calculateWorkingDays(LocalDate.of(request.getFromDate().getYear(),
                    request.getFromDate().getMonthValue(), 1));
            Timesheet newTimesheet = new Timesheet();
            newTimesheet.setIdTimesheet(idTimesheet);
            newTimesheet.setUsernameEmployee(request.getUsernameEmployee());
            newTimesheet.setYear(request.getFromDate().getYear());
            newTimesheet.setMonth(request.getFromDate().getMonthValue());
            newTimesheet.setRequiredHours((float)numberOfHours * workingDays);
            newTimesheet.setStatus(TimesheetStatus.OPENED);
            timesheet = newTimesheet;
            timesheetRepository.save(newTimesheet);
        }
        Holiday holidayReturned;
        Holiday holiday = new Holiday();
        holiday.setIdRequest(0);
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
                    if (holiday.getIdRequest() == request.getIdRequest()) {
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
}
