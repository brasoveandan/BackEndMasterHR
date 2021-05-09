package utils;

import domain.enums.AdminRole;
import domain.enums.ContractType;
import domain.enums.HolidayType;
import domain.enums.RequestStatus;

public class Utils {
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
}