package domain.validators;

import domain.Timesheet;

public class TimesheetValidator implements Validator<Timesheet> {
    @Override
    public void validate(Timesheet entity) throws ValidationException {
        String message = "";
        if (entity.getIdTimesheet() == null || entity.getIdTimesheet().equals(""))
            message += "Id timesheet invalid.";
        if (entity.getUsernameEmployee() == null || entity.getUsernameEmployee().equals(""))
            message += "Nume de utilizator invalid.";

        if (entity.getYear() != java.time.LocalDate.now().getYear())
            message += "An invalid.";
        if (entity.getMonth() < 1 || entity.getMonth() > 12)
            message += "Luna invalida.";
        if (entity.getWorkedHours() < 0)
            message += "Numarul de ore lucrate nu poate fi negativ.";
        if (entity.getHomeOfficeHours() < 0)
            message += "Numarul de ore lucrate prin telemunca nu poate fi negativ.";
        if (entity.getRequiredHours() < 0)
            message += "Numarul de ore necesare nu poate fi negativ.";
        if (entity.getTotalOvertimeLeave() < 0)
            message += "Numarul de ore libere luate din orele suplimentare nu poate fi negativ.";
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
