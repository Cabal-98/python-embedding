package it.leonardo.diabetes_prediction.data.tools;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class SplitArray {

    public static double[][] getLabels(double[][] data) {
        return Arrays.stream(data)
                .map(row -> new double[]{row[row.length - 1]})
                .toArray(double[][]::new);
    }

    public static double[][] getData(double[][] data) {
        return Arrays.stream(data)
                .map(row -> Arrays.copyOfRange(row, 0, data[0].length - 1))
                .toArray(double[][]::new);
    }

}
