package by.karpovich.cryptoWatcher.exception;

public class ImpossibleActionException extends RuntimeException {
    public ImpossibleActionException() {
    }

    public ImpossibleActionException(String message) {
        super(message);
    }
}
