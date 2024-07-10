package it.leonardo.diabetes_prediction.data.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataManager {

    public double[][] balance(double[][] data, double balanceFraction) {
        double[][] positives = Arrays.stream(data)
                .filter(row -> row[data[0].length - 1] == 1.0)
                .toArray(double[][]::new);

        int balanceNumber = (int) (positives.length * balanceFraction);

        double[][] negatives = Arrays.stream(data)
                .filter(row -> row[data[0].length -1] == 0.0)
                .toArray(double[][]::new);

        double[][] cuttedArray = new double[positives.length+balanceNumber][];

        for(int i=0; i<positives.length; i++) cuttedArray[i] = positives[i];
        negatives = shuffle(negatives);
        for(int i=0 ;i<balanceNumber;i++) cuttedArray[positives.length + i] = negatives[i];

        return shuffle(cuttedArray);
    }

    public double[][] shuffle(double[][] data) {

        List<double[]> list = new ArrayList<>(Arrays.asList(data));
        Collections.shuffle(list);
        return list.toArray(new double[data.length][]);

    }

}
