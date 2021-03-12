package domain.validators;

import domain.Timesheet;

public class TimesheetValidator implements Validator<Timesheet> {
    @Override
    public void validate(Timesheet entity) throws ValidationException {
        String message = "";
        if (entity.getIdTimesheet() < 0)
            message += "Id timesheet invalid.";
        if (entity.getUsernameEmployee() == null || entity.getUsernameEmployee().equals(""))
            message += "Nume de utilizator invalid.";
        if (entity.getIdRequest() < 0)
            message += "Id cerere invalid.";
        if (entity.getToDate().getYear() != java.time.LocalDate.now().getYear())
            message += "Data invalida";
        if (entity.getWorkedHours() < 0)
            message += "Numarul de ore lucrate nu poate fi negativ.";
        if (entity.getHomeOfficeHours() < 0)
            message += "Numarul de ore lucrate prin telemunca nu poate fi negativ.";
        if (entity.getRequiredHours() < 0)
            message += "Numarul de ore necesare nu poate fi negativ.";
        if (entity.getOvertimeLeave() < 0)
            message += "Numarul de ore libere luate din orele suplimentare nu poate fi negativ.";
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
