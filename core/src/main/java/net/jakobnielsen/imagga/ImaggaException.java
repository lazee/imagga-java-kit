package net.jakobnielsen.imagga;

public class ImaggaException extends RuntimeException {

   public ImaggaException(String message) {
        super(message);
    }

    public ImaggaException(String message, Throwable cause) {
        super(message, cause);
    }

}
