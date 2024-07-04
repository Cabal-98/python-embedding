package it.leonardo.diabetes_prediction.service;

import it.leonardo.diabetes_prediction.dto.PazienteDTO;
import it.leonardo.diabetes_prediction.exception.DynamicClassHandlingException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

@Service
public class ScriviCSVService {

    public <T> String scriviCSV(List<T> listaOutput) {
        StringWriter out = new StringWriter();

        try {
            Field[] attributi = listaOutput.getFirst().getClass().getDeclaredFields();
            String[] headers = new String[attributi.length];
            for (int i = 0; i < attributi.length; i++) {
                headers[i] = attributi[i].getName();
            }

            try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers))) {
                for (T obj : listaOutput) {

                    Object[] values = new Object[attributi.length];
                    for (int i = 0; i < attributi.length; i++) {
                        boolean wasAccessible = attributi[i].canAccess(obj);
                        attributi[i].setAccessible(true);  // Assicuriamo accesso ai campi privati
                        values[i] = attributi[i].get(obj);
                        attributi[i].setAccessible(wasAccessible);
                    }
                    printer.printRecord(values);
                }
                return out.toString();

            } catch (IOException e) {
                throw new RuntimeException("Errore nella scrittura del CSV.");
            }
        } catch (IllegalAccessException e) {
            throw new DynamicClassHandlingException("Impossibile accedere al valore dell'attributo. ", e);
        }

    }


}
