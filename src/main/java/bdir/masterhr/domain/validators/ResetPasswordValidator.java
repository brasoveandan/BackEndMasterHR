package bdir.masterhr.domain.validators;

import bdir.masterhr.domain.dtos.request.ResetPassword;

public class ResetPasswordValidator implements Validator<ResetPassword> {

    @Override
    public void validate(ResetPassword entity) throws ValidationException {
        String message = "";
        if (entity.getToken() == null || entity.getToken().equals(""))
            message += "Token invalid.";
        if (entity.getPassword() == null || entity.getPassword().length() < 8)
            message += "Parola prea scurta.";
        if (entity.getPassword() != null && !entity.getPassword().equals(entity.getPassword_confirm()))
            message += "Cele doua parole nu coincid.";

        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
