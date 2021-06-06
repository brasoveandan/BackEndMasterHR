package bdir.masterhr.domain.validators;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;

    class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
