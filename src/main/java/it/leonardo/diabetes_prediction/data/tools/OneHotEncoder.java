package it.leonardo.diabetes_prediction.data.tools;

import it.leonardo.diabetes_prediction.exception.DynamicClassHandlingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class OneHotEncoder {

    public <T> List<T> arrayColumnEncoder(Class<T> classeEncoded, List<?> objects, List<String> attributi) {


        if(objects.isEmpty()){
            throw new IllegalArgumentException("La lista degli oggetti non può essere vuota.");
        }

        try {
            Constructor<T> costruttore = classeEncoded.getDeclaredConstructor();
            List<T> listaEncoded = new ArrayList<>();
            for(int i=0; i<objects.size(); i++) {
                T nuovaIstanza = costruttore.newInstance();
                listaEncoded.add(nuovaIstanza);
            }

            Field[] campiEncoded = classeEncoded.getDeclaredFields();
            Field[] campiOriginari = objects.getFirst().getClass().getDeclaredFields();


            for(Object obj : objects) {

                for(Field oldField : campiOriginari) {

                    Field newField = trovaCampo(campiEncoded, oldField.getName());

                    int indice = objects.indexOf(obj);
                    Object choosenOne = listaEncoded.get(indice);

                    if(newField != null) {


                        boolean wasOldAccessible = oldField.canAccess(obj);
                        boolean wasNewAccessible = newField.canAccess(choosenOne);
                        oldField.setAccessible(true);
                        newField.setAccessible(true);

                        newField.set(choosenOne, oldField.get(obj));

                        oldField.setAccessible(wasOldAccessible);
                        newField.setAccessible(wasNewAccessible);
                    } else if (attributi.contains(oldField.getName())){
                        Set<String> valoriUnivoci = trovaUnivoci(objects,oldField.getName());
                        for(String nomeCampoEncoded : valoriUnivoci) {

                            newField = trovaCampo(campiEncoded, nomeCampoEncoded);

                            boolean wasNewAccessible = newField.canAccess(choosenOne);
                            boolean wasOldAccessible = oldField.canAccess(obj);

                            newField.setAccessible(true);
                            oldField.setAccessible(true);

                            Object test01 = oldField.get(obj);
                            String testStringa = test01.toString();
                            String testStringa02 = testStringa.toLowerCase();
                            boolean verificaTest = testStringa02.equals(nomeCampoEncoded.toLowerCase());
                            newField.set(choosenOne, oldField.get(obj).toString().toLowerCase().equals(nomeCampoEncoded.toLowerCase()));

                            newField.setAccessible(wasNewAccessible);
                            oldField.setAccessible(wasOldAccessible);
                        }
                    }

                }
            }

            return listaEncoded;

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new DynamicClassHandlingException("Errore durante l'encoding della classe", e);
        }

    }

    private Field trovaCampo(Field[] campi, String nomeCampo) {
        for (Field campo : campi) {
            String campoSimple = campo.getName().toLowerCase().replaceAll("\\s","");
            String nomeCampoSimple = nomeCampo.toLowerCase().replaceAll("\\s","");
            if (campoSimple.equals(nomeCampoSimple)) {
                return campo;
            }
        }
        return null;
    }

    private Set<String> trovaUnivoci(List<?> objects, String nomeCampo) {
        Set<String> valoriUnivoci = new HashSet<>();

        for(Object obj : objects) {
            try {
                Field field = obj.getClass().getDeclaredField(nomeCampo);
                boolean wasAccessible = field.canAccess(obj);
                field.setAccessible(true);
                Object value = field.get(obj);

                if (value instanceof String stringValue) {
                    valoriUnivoci.add(stringValue.toLowerCase().replaceAll("\\s",""));
                }

                field.setAccessible(wasAccessible);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new DynamicClassHandlingException("Errore nell'individuazione dinamica degli elementi dell'attributo", e);
            }
        }

        return valoriUnivoci;

    }
}
