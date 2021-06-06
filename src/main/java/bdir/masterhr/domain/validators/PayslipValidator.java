package bdir.masterhr.domain.validators;

import bdir.masterhr.domain.Payslip;

import java.time.LocalDateTime;

public class PayslipValidator implements Validator<Payslip> {
    @Override
    public void validate(Payslip entity) throws ValidationException {
        String message = "";
        if (entity.getIdPayslip() == null || entity.getIdPayslip().equals(""))
            message += "Id fluturaș salariu invalid.";
        if (entity.getUsernameEmployee() == null || entity.getUsernameEmployee().equals(""))
            message += "Nume de utilizator invalid.";
        if (entity.getYear() < 2000 || entity.getYear() > LocalDateTime.now().getYear())
            message += "Anul introdus este invalid.";
        if (entity.getMonth() < 1 || entity.getMonth() > 12 || entity.getMonth() > LocalDateTime.now().getMonthValue())
            message += "Luna introdusa este invalida.";
        if (entity.getGrossSalary() < 0)
            message += "Salariul brut nu poate fi mai mic ca 0.";
        if (entity.getNetSalary() < 0)
            message += "Salariul net nu poate fi mai mic ca 0.";
        if (entity.getWorkedHours() < 0)
            message += "Numarul de ore lucrate nu poate fi negativ.";
        if (entity.getHomeOfficeHours() < 0)
            message += "Numarul de ore lucrate prin telemunca nu poate fi negativ.";
        if (entity.getRequiredHours() < 0)
            message += "Numarul de ore necesare nu poate fi negativ.";
        if (entity.getIncreases() < 0)
            message += "Sporul nu poate fi negativ.";
        if (entity.getOvertimeIncreases() < 0)
            message += "Sporul din orele suplimentare nu poate fi negativ.";
        if (entity.getTicketsValue() < 0)
            message += "Valoarea bonurilor de masa nu poate fi negativa.";
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
