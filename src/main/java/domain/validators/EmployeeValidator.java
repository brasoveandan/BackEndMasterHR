package domain.validators;

import domain.Employee;

public class EmployeeValidator implements Validator<Employee> {
    @Override
    public void validate(Employee employee) throws ValidationException {
        String message="";
        if (employee.getUsername() == null || employee.getUsername().equals("")) {
            message += "Nume de utilizator invalid.\n";
        }
        if (employee.getPassword() == null || employee.getPassword().equals("")) {
            message += "Parola invalida.\n";
        }
        if (employee.getFirstName() == null || employee.getFirstName().equals("")) {
            message += "Prenumele nu poate fi vid.\n";
        }
        if (employee.getLastName() == null || employee.getLastName().equals("")) {
            message += "Numele nu poate fi vid.\n";
        }
        if (employee.getCnp() == null || employee.getCnp().length()!=13) {
            message += "CNP-ul trebuie sa contina 13 cifre.\n";
        }
        if (!message.equals("")) {
            throw new Validator.ValidationException(message);
        }
    }
}
