package domain.validators;

import domain.Holiday;

public class HolidayValidator implements Validator<Holiday> {
    @Override
    public void validate(Holiday entity) throws ValidationException {
        String message = "";
        if (entity.getIdHoliday() < 0)
            message += "Id vacanta invalid.";
        if (entity.getUsernameEmployee() == null || entity.getUsernameEmployee().equals(""))
            message += "Nume de utilizator invalid.";
        if (entity.getIdRequest() < 0)
            message += "Id cerere invalid.";
        if (entity.getFromDate().after(entity.getToDate()))
            message += "Data de inceput trebuie sa fie inaintea datei de sfarsit.";
        if (entity.getFromDate().after(entity.getToDate()))
            message += "Data de inceput trebuie sa fie inaintea datei de sfarsit.";
        if ((entity.getFromDate().getYear() != java.time.LocalDate.now().getYear()) ||
                (entity.getToDate().getYear()) != java.time.LocalDate.now().getYear())
            message += "Data invalida.";
        if ((entity.getToDate().getDay() - entity.getFromDate().getDay()) != entity.getDaysOff())
            message += "Numar zile concediu calculat gresit";
        if (!entity.getType().toString().equals("BLOOD_DONATIONS") && !entity.getType().toString().equals("MARRIAGE")
                && !entity.getType().toString().equals("NORMAL") && entity.getType().toString().equals("FUNERAL"))
            message += "Tipul concediului trebuie sa fie BLOOD_DONATIONS, MARRIAGE, FUNERAL sau NORMAL.";
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
