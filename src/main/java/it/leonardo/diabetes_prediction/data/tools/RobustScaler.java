package it.leonardo.diabetes_prediction.data.tools;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RobustScaler {

    public static double[][] robustScaleMatrix(double[][] data, Map<String, Integer> mappaInidici, List<String> daScalare) {

        double[][] scaledData = copyMatrix(data);
        List<Integer> indexOfScaling = findIndexes(mappaInidici,daScalare);

        for(int i=0; i<data.length; i++) {
            scaledData[i] = robustScale(scaledData[i],indexOfScaling);
        }

        return scaledData;
    }

    public static double[] robustScale(double[] data,List<Integer> indexOfScaling) {
        // Copiamo i dati originali per non modificare l'array originale
        double[] scaledData = Arrays.copyOf(data, data.length);

        // Calcoliamo il 25° e il 75° percentile
        double q1 = percentile(scaledData, 25);
        double q3 = percentile(scaledData, 75);
        double iqr = q3 - q1;

        // Ridimensioniamo i dati usando i percentili
        for (int i : indexOfScaling) {
            scaledData[i] = (scaledData[i] - q1) / iqr;
        }

        return scaledData;
    }

    private static double percentile(double[] data, double percentile) {
        Arrays.sort(data);
        int index = (int) Math.ceil(percentile / 100.0 * data.length);
        return data[Math.min(index, data.length - 1)];
    }

    private static double[][] copyMatrix(double[][] data) {
        return Arrays.stream(data)
                .map(double[]::clone)
                .toArray(double[][]::new);
    }

    private static List<Integer> findIndexes(Map<String,Integer> mappaIndici, List<String> colonne) {
        return colonne.stream()
                .map(mappaIndici::get)
                .collect(Collectors.toList());}

}
