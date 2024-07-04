package it.leonardo.diabetes_prediction.data.converter;

import it.leonardo.diabetes_prediction.exception.ValueToFloatException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class ClassToDoubleArrayService {

    public double[][] listToDoubleArray(List<?> objectList) {

        if(objectList.isEmpty()){
            throw new IllegalArgumentException("La lista degli oggetti non può essere vuota.");
        }

        int classFieldsNumber = objectList.getFirst().getClass().getDeclaredFields().length;
        double[][] array = new double[objectList.size()][classFieldsNumber];


        for(int i=0; i<objectList.size(); i++) {
            array[i] = convertToDoubleArray(objectList.get(i));
        }

        return array;

    }


    private double[] convertToDoubleArray(Object object) {
        Class<?> classe = object.getClass();
        Field[] attributi = classe.getDeclaredFields();
        double[] arrayTensore = new double[attributi.length];

        for(int i=0; i<attributi.length; i++) {
            Field attributo = attributi[i];
            boolean privilegiAccesso = attributo.canAccess(object);
            try {
                Object valore = attributo.get(object);
                attributo.setAccessible(true);
                if(valore instanceof Double) {
                    arrayTensore[i] = (Double) attributo.get(object);
                } else if(valore instanceof Number) {
                    arrayTensore[i] = ((Number) attributo.get(object)).doubleValue();
                } else {
                    throw new ValueToFloatException("Il valore dell'attributo non può essere convertito in float");
                }
            } catch (Exception e) {
                throw new ValueToFloatException("Impossibile convertire una la classe in un array.");
            }
            if(!privilegiAccesso) {
                try {
                    attributo.setAccessible(false);
                } catch (SecurityException e) {
                    throw new SecurityException("Impossibile ripristinare lo stato di accessibilità dell'attributo.");
                }
            }
        }

        return arrayTensore;
    }

}
