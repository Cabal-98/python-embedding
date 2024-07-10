package it.leonardo.diabetes_prediction.data.tools;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import smile.neighbor.KDTree;
import smile.neighbor.Neighbor;

import java.util.*;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class Smoter {

    private Random random;

    public double[][] smote(double[][] data, double smoteValue) {

        double[][] positives = Arrays.stream(data)
                .filter(row -> row[data[0].length - 1] == 1.0)
                .toArray(double[][]::new);
        double[][] smotePoints = new double[(int) (positives.length*smoteValue)][];

        int k = (int) Math.round(smoteValue);

        KDTree<Void> kdTree = new KDTree<>(positives,null);

        for(int i=0; i<smotePoints.length; i++) {
            int randomIndex = random.nextInt(positives.length);
            List<double[]> newPoints;
            Neighbor<double[],Void>[] neighbors;
            neighbors = kdTree.search(positives[randomIndex],k);
            newPoints = fineFillingNeighbor(findInTheMiddle(neighborToList(neighbors),positives[randomIndex]));

            smotePoints = appendRows(smotePoints,newPoints);
        }

        return appendRows(data, Arrays.stream(smotePoints).toList());
    }

    private List<double[]> neighborToList(Neighbor<double[],Void>[] neighbors) {
        List<double[]> points = new ArrayList<>();
        for (Neighbor<double[], Void> neighbor : neighbors) {
            points.add(neighbor.key);
        }
        return points;
    }

    private double[][] appendRows(double[][] smotePoints, List<double[]> newPoints) {
        int originalRows = smotePoints.length;
        int newRows = newPoints.size();

        double[][] newSmotedPoints = new double[originalRows+newRows][];

        for(int i=0; i<originalRows; i++) {
            newSmotedPoints[i] = smotePoints[i].clone();
        }

        for(int i=0; i<newRows; i++) {
            newSmotedPoints[originalRows+i] = newPoints.get(i).clone();
        }

        return newSmotedPoints;

    }

    private List<double[]> findInTheMiddle(List<double[]> neighbors, double[] point) {

        double rndPick = random.nextDouble();

        for(double[] vicino : neighbors) {
            for(int i=0; i<point.length; i++) {
                vicino[i] = point[i] + (rndPick * (vicino[i] - point[i]));
            }
        }

        return neighbors;

    }

    private List<double[]> fineFillingNeighbor(List<double[]> neighbors) {

        IntStream.range(0, neighbors.size()).forEach(i -> {
            neighbors.set(i, chooseGender(neighbors.get(i)));
            neighbors.set(i, chooseSmoker(neighbors.get(i)));
            neighbors.set(i, chooseHeartD(neighbors.get(i)));
            neighbors.set(i, setDiabetes1(neighbors.get(i)));
            neighbors.set(i, roundInteger(neighbors.get(i)));
        });

        return neighbors;

    }

    private double[] chooseGender(double[] riga) {

        double[] genders = {riga[6],riga[7],riga[8]};
        int maxIndex = findMax(genders);

        genders[0] = 0;
        genders[1] = 0;
        genders[2] = 0;
        genders[maxIndex] = 1;

        riga[6] = genders[0];
        riga[7] = genders[1];
        riga[8] = genders[2];

        return riga;
    }

    private double[] chooseSmoker(double[] riga) {

        double[] smokers = {riga[9],riga[10],riga[11],riga[12],riga[13],riga[14]};
        int maxIndex = findMax(smokers);

        smokers[0] = 0;
        smokers[1] = 0;
        smokers[2] = 0;
        smokers[3] = 0;
        smokers[4] = 0;
        smokers[5] = 0;
        smokers[maxIndex] = 1;

        riga[9] = smokers[0];
        riga[10] = smokers[1];
        riga[11] = smokers[2];
        riga[12] = smokers[3];
        riga[13] = smokers[4];
        riga[14] = smokers[5];

        return riga;
    }

    private double[] chooseHeartD(double[] riga) {

        if(riga[1] >= 0.5) {
            riga[1] = 1;
        } else {
            riga[1] = 0;
        }

        if(riga[2] >= 0.5) {
            riga[2] = 1;
        } else {
            riga[2] = 0;
        }

        return riga;
    }

    private double[] setDiabetes1(double[] riga) {
        riga[15]=1;
        return riga;
    }

    private int findMax(double[] array) {
        double maxValue = array[0];
        int maxIndex = 0;
        for(int i=1; i<array.length; i++) {
            if(array[i]>maxValue) {
                maxValue = array[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private double[] roundInteger(double[] riga) {
        riga[0] = Math.round(riga[0]);
        riga[5] = Math.round(riga[5]);
        return riga;
    }

}
