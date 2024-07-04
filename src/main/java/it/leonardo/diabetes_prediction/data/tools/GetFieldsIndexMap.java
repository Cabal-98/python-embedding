package it.leonardo.diabetes_prediction.data.tools;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Component
public class GetFieldsIndexMap {

    public Map<String, Integer> getIndexMap(Class<?> classe) {

        if(classe == null){
            throw new IllegalArgumentException("La classe non può essere 'null'.");
        }

        Map<String, Integer> mappaIndici = new java.util.HashMap<>();

        Field[] attributi = classe.getDeclaredFields();
        for(int i=0; i<attributi.length; i++) {
            mappaIndici.put(attributi[i].getName(),i);
        }

        return mappaIndici;
    }

}
