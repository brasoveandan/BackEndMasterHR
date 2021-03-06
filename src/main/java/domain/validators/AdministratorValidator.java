package domain.validators;

import domain.Administrator;

public class AdministratorValidator implements Validator<Administrator> {
    @Override
    public void validate(Administrator entity) throws ValidationException {
        String message="";
        if (entity.getUsernameEmployee() == null || entity.getUsernameEmployee().equals(""))
            message += "Nume de utilizator invalid.";
        if (!entity.getAdminRole().toString().equals("GROUP_LEADER") && !entity.getAdminRole().toString().equals("HR_EMPLOYEE") && !entity.getAdminRole().toString().equals("HR_RESPONSIVE_DEPARTMENT"))
            message += "Rolul trebuie sa fie GROUP_LEADER, HR_EMPLOYEE sau HR_RESPONSIVE_DEPARTMENT.";
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
