package se.callista.blog.service.services;

public class RunLiquibaseException extends RuntimeException {

    public RunLiquibaseException(String message) {
        super(message);
    }

    public RunLiquibaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public RunLiquibaseException(Throwable cause) {
        super(cause);
    }
}
