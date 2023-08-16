package by.karpovich.security.exception;

public class ImpossibleActionException extends RuntimeException {
    public ImpossibleActionException() {
    }

    public ImpossibleActionException(String message) {
        super(message);
    }
}
