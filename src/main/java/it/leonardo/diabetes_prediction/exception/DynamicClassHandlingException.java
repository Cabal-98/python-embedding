package it.leonardo.diabetes_prediction.exception;

public class DynamicClassHandlingException extends RuntimeException {

    public DynamicClassHandlingException(String message) { super(message); }
    public DynamicClassHandlingException(String message, Exception cause) { super(message,cause);}

}
