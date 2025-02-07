package gojosatoru.exceptions;

public class InvalidCommandException extends GojoException {
    public InvalidCommandException() {
        super("   ____________________________________________________________\n  "
            + " Oi... I don't know what that means, didn't teach ya that in Jujutsu High..\n"
            + "   ____________________________________________________________");
    }

    public InvalidCommandException(String message) {
        super(message); // Allow custom error messages
    }
}
