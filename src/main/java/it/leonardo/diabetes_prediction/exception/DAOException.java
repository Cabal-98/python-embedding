package it.leonardo.diabetes_prediction.exception;

public class DAOException extends RuntimeException {

    public DAOException(String message) { super(message); }
    public DAOException(String message, Exception cause) { super(message,cause);}

}
