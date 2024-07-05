package it.leonardo.diabetes_prediction.data.tools;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RobustScaler {

    public static double[][] robustScaleMatrix(double[][] data, Map<String, Integer> mappaIndici, List<String> daScalare) {
        double[][] scaledData = copyMatrix(data);
        List<Integer> indexOfScaling = findIndexes(mappaIndici, daScalare);

        for (Integer colIndex : indexOfScaling) {
            double[] columnData = getColumn(scaledData, colIndex);
            double[] scaledColumn = robustScale(columnData, indexOfScaling, columnData.length);
            setColumn(scaledData, colIndex, scaledColumn);
        }

        return scaledData;
    }

    public static double[] robustScale(double[] data, List<Integer> indexOfScaling, int dataLength) {
        double[] scaledData = Arrays.copyOf(data, data.length);

        double q1 = percentile(scaledData, 25, dataLength);
        double q3 = percentile(scaledData, 75, dataLength);
        double iqr = q3 - q1;

        for (int i=0; i<scaledData.length; i++) {
                scaledData[i] = (scaledData[i] - q1) / iqr;
        }

        return scaledData;
    }

    private static double percentile(double[] data, double percentile, int dataLength) {
        Arrays.sort(data);
        int index = (int) Math.ceil(percentile / 100.0 * dataLength) - 1;
        return data[Math.min(index, dataLength - 1)];
    }

    private static double[][] copyMatrix(double[][] data) {
        return Arrays.stream(data)
                .map(double[]::clone)
                .toArray(double[][]::new);
    }

    private static List<Integer> findIndexes(Map<String,Integer> mappaIndici, List<String> colonne) {
        return colonne.stream()
                .map(colonna -> {
                    String colonnaToLowerCase = colonna.toLowerCase();
                    return mappaIndici.getOrDefault(colonnaToLowerCase,-1);
                })
                .filter(indice -> indice != -1)
                .collect(Collectors.toList());
    }


    private static double[] getColumn(double[][] data, int columnIndex) {
        double[] column = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            column[i] = data[i][columnIndex];
        }
        return column;
    }

    private static void setColumn(double[][] data, int columnIndex, double[] column) {
        for (int i = 0; i < data.length; i++) {
            data[i][columnIndex] = column[i];
        }
    }

}
