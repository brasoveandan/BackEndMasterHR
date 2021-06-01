package domain.validators;

import domain.Clocking;

import java.time.LocalDateTime;

public class ClockingValidator implements Validator<Clocking> {
    @Override
    public void validate(Clocking entity) throws ValidationException {
        String message = "";
        if (entity.getIdClocking() == null || entity.getIdClocking().equals(""))
            message += "Id invalid.";
        if (entity.getFromHour().getYear() != entity.getToHour().getYear())
            message += "Ora de iesire si ora de intrare trebuie sa fie in acelasi an.";
        if (entity.getFromHour().getMonthValue() != entity.getToHour().getMonthValue())
            message += "Ora de iesire si ora de intrare trebuie sa fie in aceeasi luna.";
        if (entity.getFromHour().getDayOfMonth() != entity.getToHour().getDayOfMonth())
            message += "Ora de iesire si ora de intrare trebuie sa fie in aceeasi zi.";
        if (entity.getFromHour().isAfter(LocalDateTime.now()))
            message += "Pontare invalida.";
        if (!message.equals("")) {
            throw new ValidationException(message);
        }
    }
}
