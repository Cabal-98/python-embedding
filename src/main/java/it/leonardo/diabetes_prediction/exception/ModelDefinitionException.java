package it.leonardo.diabetes_prediction.exception;

public class ModelDefinitionException extends RuntimeException{

    public ModelDefinitionException(String message) { super(message); }
    public ModelDefinitionException(String message, Exception cause) { super(message,cause);}

}
