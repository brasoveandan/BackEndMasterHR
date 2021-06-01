package controller;

import domain.Holiday;
import domain.dtos.response.HolidayDTO;
import domain.dtos.response.SummaryHolidayDTO;
import domain.enums.HolidayType;
import domain.enums.RequestStatus;
import domain.validators.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ContractRepository;
import repository.HolidayRepository;
import repository.RequestRepository;
import utils.Utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.temporal.ChronoUnit.DAYS;

@CrossOrigin
@RestController
public class HolidayController {
    private static final Set<HolidayType> OTHER_TYPES = Collections.unmodifiableSet(EnumSet.of(HolidayType.BLOOD_DONATION, HolidayType.MARRIAGE, HolidayType.FUNERAL));
    private final ContractRepository contractRepository = new ContractRepository();
    private final RequestRepository requestRepository = new RequestRepository();
    private final HolidayRepository holidayRepository = new HolidayRepository();

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
            if (holiday.getUsernameEmployee().equals(usernameEmployee) && requestRepository.findOne(String.valueOf(holiday.getIdRequest())).getStatus() == RequestStatus.ACCEPTED)  {
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
        summaryHolidayDTO.setDaysAvailable((contractRepository.findOne(usernameEmployee) == null) ? 0 : contractRepository.findOne(usernameEmployee).getDaysOff());
        return new ResponseEntity<>(summaryHolidayDTO, HttpStatus.OK);
    }

    @GetMapping("/holiday/{usernameEmployee}")
    public ResponseEntity<List<HolidayDTO>> getHolidays(@PathVariable String usernameEmployee) {
        List<HolidayDTO> holidayDTOList = new ArrayList<>();
        holidayRepository.findAll().forEach(holiday -> {
            if (holiday.getUsernameEmployee().equals(usernameEmployee) ) {
                HolidayDTO holidayDTO = new HolidayDTO();
                holidayDTO.setIdRequest(holiday.getIdRequest());
                holidayDTO.setUser(holiday.getUsernameEmployee());
                holidayDTO.setFromDate(holiday.getFromDate());
                holidayDTO.setToDate(holiday.getToDate());
                holidayDTO.setNumberOfDays((int) DAYS.between(holiday.getFromDate(), holiday.getToDate()));
                holidayDTO.setProxyUsername(holiday.getProxyUsername());
                holidayDTO.setType(Utils.holidayTypeToString(holiday.getType()));
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

    @GetMapping("/holidayRequest/{usernameEmployee}")
    public ResponseEntity<List<HolidayDTO>> getAllHolidayRequests(@PathVariable String usernameEmployee) {
        List<HolidayDTO> holidayDTOList = new ArrayList<>();
        String department = contractRepository.findOne(usernameEmployee) == null ? "" : contractRepository.findOne(usernameEmployee).getDepartment();
        holidayRepository.findAll().forEach(holiday -> {
            if (contractRepository.findOne(holiday.getUsernameEmployee()).getDepartment().equals(department)) {
                HolidayDTO holidayDTO = new HolidayDTO();
                holidayDTO.setIdRequest(holiday.getIdRequest());
                holidayDTO.setUser(holiday.getUsernameEmployee());
                holidayDTO.setFromDate(holiday.getFromDate());
                holidayDTO.setToDate(holiday.getToDate());
                holidayDTO.setNumberOfDays((int) DAYS.between(holiday.getFromDate(), holiday.getToDate()));
                holidayDTO.setProxyUsername(holiday.getProxyUsername());
                holidayDTO.setType(Utils.holidayTypeToString(holiday.getType()));
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
        holidayDTOList.sort(Comparator.comparing(HolidayDTO::getFromDate).reversed().thenComparing(HolidayDTO::getStatus));
        return new ResponseEntity<>(holidayDTOList, HttpStatus.OK);
    }
}
