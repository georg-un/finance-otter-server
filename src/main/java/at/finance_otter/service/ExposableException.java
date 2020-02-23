package at.finance_otter.service;

public class ExposableException extends Exception {
    public ExposableException(String errorMessage) {
        super(errorMessage);
    }
}
