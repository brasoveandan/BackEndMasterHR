package domain.validators;

import domain.Contract;

public class ContractValidator implements Validator<Contract> {
    @Override
    public void validate(Contract entity) throws ValidationException {
        String message = "";
        if (entity.getUsernameEmployee() == null || entity.getUsernameEmployee().equals(""))
            message += "Nume de utilizator invalid.";
        if (entity.getCompanyName() == null || entity.getCompanyName().equals(""))
            message += "Numele companiei nu poate fi vid.";
        if (entity.getGrossSalary() < 0)
            message += "Salariul este invalid.";
        if (entity.getHireDate() == null)
            message += "Data de angajare invalida.";
        if (!entity.getType().toString().equals("FULL_TIME") && !entity.getType().toString().equals("PART_TIME_6") && !entity.getType().toString().equals("PART_TIME_4"))
            message += "Rolul trebuie sa fie FULL_TIME, PART_TIME_6 sau PART_TIME_4.";
        if (entity.getExpirationDate() != null && entity.getHireDate() != null && entity.getHireDate().isAfter(entity.getExpirationDate()))
            message += "Data de angajare trebuie sa fie inainte datei de expirare a contractului.";
        if (entity.getDepartment() == null || entity.getDepartment().equals(""))
            message += "Departament invalid.";
        if (entity.getPosition() == null || entity.getPosition().equals(""))
            message += "Pozitie angajat invalida.";
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
