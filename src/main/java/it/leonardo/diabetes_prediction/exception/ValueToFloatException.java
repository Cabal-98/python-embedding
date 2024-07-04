package it.leonardo.diabetes_prediction.exception;

public class ValueToFloatException extends RuntimeException {

    public ValueToFloatException(String message) { super(message); }
    public ValueToFloatException(String message, Exception cause) { super(message,cause);}

}
