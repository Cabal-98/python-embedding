package it.leonardo.diabetes_prediction.data.tools;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class GetFieldsIndexMap {

    public <T> Map<String, Integer> getIndexMap(Class<T> classe) {

        if(classe == null){
            throw new IllegalArgumentException("La classe non può essere 'null'.");
        }

        Map<String, Integer> mappaIndici = new java.util.HashMap<>();

        Field[] attributi = classe.getDeclaredFields();
        for(int i=0; i<attributi.length; i++) {
            String nomeCampo = attributi[i].getName().toLowerCase();
            mappaIndici.put(nomeCampo,i);
        }

        return mappaIndici;
    }

}
