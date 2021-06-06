package bdir.masterhr.utils;

import bdir.masterhr.domain.Contract;
import bdir.masterhr.domain.Employee;
import bdir.masterhr.domain.dtos.response.ContractDTO;
import bdir.masterhr.domain.enums.ContractType;
import bdir.masterhr.domain.enums.HolidayType;
import bdir.masterhr.domain.enums.RequestStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static String holidayTypeToString(HolidayType holidayType) {
        switch (holidayType) {
            case FUNERAL:
                return "Concediu pentru participare la funerali";
            case BLOOD_DONATION:
                return "Concediu pentru donare sange";
            case MARRIAGE:
                return "Concediu pentru pariticipare la nunta";
            case OVERTIME_LEAVE:
                return "Concediu din ore suplimentare";
            case MEDICAL:
                return "Concediu medical";
            default:
                return "Concediu normal anual";
        }
    }

    public static HolidayType stringToHolidayType(String string) {
        switch (string) {
            case "Concediu pentru participare la funerali":
                return HolidayType.FUNERAL;
            case "Concediu pentru donare sange":
                return HolidayType.BLOOD_DONATION;
            case "Concediu pentru participare la nunta":
                return HolidayType.MARRIAGE;
            case "Concediu din ore suplimentare":
                return HolidayType.OVERTIME_LEAVE;
            case "Concediu medical":
                return HolidayType.MEDICAL;
            default:
                return HolidayType.NORMAL;
        }
    }

    public static String requestStatusToString(RequestStatus requestStatus) {
        switch (requestStatus) {
            case ACCEPTED:
                return "ACCEPTAT";
            case DECLINED:
                return "RESPINS";
            default:
                return "IN ASTEPTARE";
        }
    }

    public static RequestStatus stringToRequestStatus(String string) {
        switch (string) {
            case "ACCEPTAT":
                return RequestStatus.ACCEPTED;
            case "RESPINS":
                return RequestStatus.DECLINED;
            default:
                return RequestStatus.PENDING;
        }
    }

    public static String contractTypeToString(ContractType contractType) {
        switch (contractType) {
            case PART_TIME_4:
                return "Norma 4 ore / zi";
            case PART_TIME_6:
                return "Norma 6 ore / zi";
            default:
                return "Norma 8 ore / zi";
        }
    }

    public static ContractType stringToContractType(String string) {
        switch (string) {
            case "Norma 4 ore / zi":
                return ContractType.PART_TIME_4;
            case "Norma 6 ore / zi":
                return ContractType.PART_TIME_6;
            default:
                return ContractType.FULL_TIME_8;
        }
    }

    public static Contract contractDTOToContract(ContractDTO contractDTO) {
        Contract contract = new Contract();
        contract.setUsernameEmployee(contractDTO.getUsername());
        contract.setCompanyName(contractDTO.getCompanyName());
        contract.setBaseSalary(contractDTO.getBaseSalary());
        contract.setCurrency(contractDTO.getCurrency());
        contract.setHireDate(contractDTO.getHireDate());
        contract.setType(Utils.stringToContractType(contractDTO.getType()));
        contract.setExpirationDate(contractDTO.getExpirationDate());
        contract.setDepartment(contractDTO.getDepartment());
        contract.setPosition(contractDTO.getPosition());
        contract.setOvertimeIncreasePercent(contractDTO.getOvertimeIncreasePercent());
        contract.setTaxExempt(contractDTO.isTaxExempt());
        contract.setDaysOff(contractDTO.getDaysOff());
        contract.setTicketValue(contractDTO.getTicketValue());
        return contract;
    }

    public static Employee contractDTOToEmployee(ContractDTO contractDTO, Employee employee) {
        employee.setPersonalNumber(contractDTO.getPersonalNumber());
        employee.setFirstName(contractDTO.getFirstName());
        employee.setLastName(contractDTO.getLastName());
        employee.setMail(contractDTO.getMail());
        employee.setPhoneNumber(contractDTO.getPhoneNumber());
        employee.setSocialSecurityNumber(contractDTO.getSocialSecurityNumber());
        employee.setBirthday(contractDTO.getBirthday());
        employee.setGender(contractDTO.getGender());
        employee.setBankName(contractDTO.getBankName());
        employee.setBankAccountNumber(contractDTO.getBankAccountNumber());
        return employee;
    }

    public static int calculateWorkingDays(LocalDate startDate) {
        int workDays = 0;
        YearMonth yearMonthObject = YearMonth.of(startDate.getYear(), startDate.getMonthValue());
        int daysInMonth = yearMonthObject.lengthOfMonth();
        while (daysInMonth > 0) {
            if (startDate.getDayOfWeek() != DayOfWeek.SATURDAY && startDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workDays++;
            }
            startDate = startDate.plusDays(1);
            daysInMonth--;
        }
        return workDays;
    }
}