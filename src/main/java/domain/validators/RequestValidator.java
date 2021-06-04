package domain.validators;

import domain.Request;


public class RequestValidator implements Validator<Request> {
    @Override
    public void validate(Request entity) throws ValidationException {
        String message = "";
        if (entity.getUsernameEmployee() == null || entity.getUsernameEmployee().equals(""))
            message += "Nume de utilizator invalid.";
        if (entity.getSubmittedDate().getYear() != java.time.LocalDate.now().getYear())
            message += "Se pot inregistra cereri doar pentru anul in curs.";
        if (!message.equals(""))
            throw new Validator.ValidationException(message);
    }
}
