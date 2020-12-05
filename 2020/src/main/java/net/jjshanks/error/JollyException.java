package net.jjshanks.error;

public class JollyException extends Exception {

    private static final long serialVersionUID = 1L;

    public JollyException(String message) {
        super(message);
    }

    public JollyException(String message, Throwable cause) {
        super(message, cause);
    }

    public JollyException(Throwable cause) {
        super(cause);
    }

}
