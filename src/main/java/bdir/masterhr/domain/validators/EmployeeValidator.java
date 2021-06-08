package bdir.masterhr.domain.validators;

import bdir.masterhr.domain.Employee;

import java.util.regex.Pattern;

public class EmployeeValidator implements Validator<Employee> {
    public static boolean mailValidation(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void validateForAdmin(Employee employee) throws ValidationException {
        String message = "";
        if (employee.getUsername() == null || employee.getUsername().equals("")) {
            message += "Nume de utilizator invalid.\n";
        }
        if (employee.getPassword() == null || employee.getPassword().length() < 6) {
            message += "Parola invalida.\n";
        }
        if (employee.getPersonalNumber() == null || employee.getPersonalNumber().length() != 10) {
            message += "Numar personal invalid.\n";
        }
        if (employee.getAdminRole() != null && !employee.getAdminRole().toString().equals("GROUP_LEADER") &&
                !employee.getAdminRole().toString().equals("HR_EMPLOYEE") &&
                !employee.getAdminRole().toString().equals("DEFAULT"))
            message += "Rolul trebuie sa fie ADMIN, GROUP_LEADER, HR_EMPLOYEE sau DEFAULT.";
        if (employee.getFirstName() == null || employee.getFirstName().equals("")) {
            message += "Prenumele nu poate fi vid.\n";
        }
        if (employee.getLastName() == null || employee.getLastName().equals("")) {
            message += "Numele nu poate fi vid.\n";
        }
        if (!mailValidation(employee.getMail())) {
            message += "Email invalid.\n";
        }
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }

    @Override
    public void validate(Employee employee) throws ValidationException {
        String message = "";
        if (employee.getUsername() == null || employee.getUsername().equals("")) {
            message += "Nume de utilizator invalid.\n";
        }
        if (employee.getPassword() == null || employee.getPassword().equals("")) {
            message += "Parola invalida.\n";
        }
        if (employee.getPersonalNumber() == null || employee.getPersonalNumber().length() != 10) {
            message += "Cod numeric invalid.\n";
        }
        if (employee.getAdminRole() != null && !employee.getAdminRole().toString().equals("GROUP_LEADER") &&
                !employee.getAdminRole().toString().equals("HR_EMPLOYEE") &&
                !employee.getAdminRole().toString().equals("DEFAULT"))
            message += "Rolul trebuie sa fie ADMIN, GROUP_LEADER, HR_EMPLOYEE sau DEFAULT.";
        if (employee.getFirstName() == null || employee.getFirstName().equals("")) {
            message += "Prenumele nu poate fi vid.\n";
        }
        if (employee.getLastName() == null || employee.getLastName().equals("")) {
            message += "Numele nu poate fi vid.\n";
        }
        if (!mailValidation(employee.getMail())) {
            message += "E-mail invalid.\n";
        }
        if (employee.getPhoneNumber() == null || employee.getPhoneNumber().length() != 10) {
            message += "Numar de telefon invalid.\n";
        }
        if (employee.getSocialSecurityNumber() == null || employee.getSocialSecurityNumber().length() != 13) {
            message += "CNP-ul trebuie sa contina 13 cifre.\n";
        }
        if (employee.getBirthday() == null || employee.getBirthday().equals("null")) {
            message += "Data nastere invalida.\n";
        }
        if (employee.getGender() == null || employee.getGender().equals("null")) {
            message += "Gen invalid.\n";
        }
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
