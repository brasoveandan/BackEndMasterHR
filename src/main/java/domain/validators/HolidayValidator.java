package domain.validators;

import domain.Holiday;

public class HolidayValidator implements Validator<Holiday> {
    @Override
    public void validate(Holiday entity) throws ValidationException {
        String message = "";
        if (entity.getIdHoliday() == null || entity.getIdHoliday().equals(""))
            message += "Id vacanta invalid.";
        if (entity.getUsernameEmployee() == null || entity.getUsernameEmployee().equals(""))
            message += "Nume de utilizator invalid.";
        if (entity.getFromDate().isAfter(entity.getToDate()))
            message += "Data de inceput trebuie sa fie inaintea datei de sfarsit.";
        if (entity.getFromDate().isAfter(entity.getToDate()))
            message += "Data de inceput trebuie sa fie inaintea datei de sfarsit.";
        if ((entity.getFromDate().getYear() != java.time.LocalDate.now().getYear()) ||
                (entity.getToDate().getYear()) != java.time.LocalDate.now().getYear())
            message += "Data invalida.";
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
