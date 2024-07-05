package it.leonardo.diabetes_prediction.data.converter;

import it.leonardo.diabetes_prediction.exception.ValueToFloatException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
                attributo.setAccessible(true);
                Object valore = attributo.get(object);
                if(valore instanceof Double) {
                    arrayTensore[i] = (Double) attributo.get(object);
                } else if(valore instanceof Number) {
                    arrayTensore[i] = ((Number) attributo.get(object)).doubleValue();
                } else if(valore instanceof Boolean) {
                    Boolean booleanValue = (Boolean) attributo.get(object);
                    arrayTensore[i] = Boolean.TRUE.equals(booleanValue) ? 1.0 : 0.0;
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

    public static <T> T doubleArrayToObject(double[] array, Class<T> clazz)
            throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        Constructor<T> constructor = clazz.getConstructor();
        T object = constructor.newInstance(); // Creazione di una nuova istanza dell'oggetto

        Field[] fields = clazz.getDeclaredFields(); // Ottenere tutti i campi della classe

        // Assicurati che il numero di array corrisponda al numero di campi nella classe
        if (array.length != fields.length) {
            throw new IllegalArgumentException("Il numero di array non corrisponde al numero di campi nella classe.");
        }

        // Itera sui campi e imposta i valori dall'array corrispondente
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true); // Rendi accessibile il campo (potrebbe essere privato)

            // Ottieni il valore dall'array e imposta il campo
            Class<?> fieldType = field.getType();

            if (fieldType.equals(double.class)) {
                field.set(object, array[i]);
            } else if(fieldType.equals(int.class)) {
                field.set(object, (int) array[i]);
            } else if(fieldType.equals(boolean.class)) {
                field.set(object, array[i] == 1.0);
            } else if(fieldType.equals(float.class)) {
                field.set(object, (float) array[i]);
            } else {
                throw new IllegalArgumentException("Tipo di campo non supportato: " + fieldType.getName());
            }

            field.setAccessible(false); // Ripristina lo stato di accessibilità del campo
        }

        return object;
    }

    public <T> List<T> dobuleToObjectList(double[][] array, Class<T> classe) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {

        List<T> lista = new ArrayList<>();

        for(int i=0; i<array.length; i++) {
            lista.add(doubleArrayToObject(array[i],classe));
        }
        return lista;
    }

}
