package it.leonardo.diabetes_prediction.data.tools;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class SplitArray {

    public static double[] getLabels(double[][] data) {
        return Arrays.stream(data)
                .mapToDouble(row -> row[row.length - 1])
                .toArray();
    }

    public static double[][] getData(double[][] data) {
        return Arrays.stream(data)
                .map(row -> Arrays.copyOfRange(row, 0, data[0].length - 1))
                .toArray(double[][]::new);
    }

    public static Map<String, double[][]> splitValidation(double[][] data, float valPercent) {
        Map<String, double[][]> allData = new HashMap<>();

        int valSize = (int) (data.length * valPercent);

        // Ottieni gli indici delle righe
        int[] allIndices = IntStream.range(0, data.length).toArray();

        // Crea il set di validazione
        double[][] validationData = Arrays.stream(allIndices)
                .limit(valSize)
                .mapToObj(i -> data[i])
                .toArray(double[][]::new);

        // Crea il set di addestramento
        double[][] trainingData = Arrays.stream(allIndices)
                .skip(valSize)
                .mapToObj(i -> data[i])
                .toArray(double[][]::new);

        // Inserisci entrambi i set nella mappa
        allData.put("validation", validationData);
        allData.put("training", trainingData);

        return allData;

    }


}
