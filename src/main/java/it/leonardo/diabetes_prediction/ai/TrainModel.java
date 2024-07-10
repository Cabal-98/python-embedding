package it.leonardo.diabetes_prediction.ai;

import ai.djl.*;
import ai.djl.basicdataset.cv.classification.*;
import ai.djl.metric.Metrics;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.training.*;
import ai.djl.training.dataset.ArrayDataset;
import ai.djl.training.dataset.Batch;
import ai.djl.training.evaluator.Accuracy;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import ai.djl.training.optimizer.Optimizer;
import ai.djl.translate.TranslateException;


import it.leonardo.diabetes_prediction.data.datasets.CustomDataset;
import it.leonardo.diabetes_prediction.data.tools.SplitArray;
import it.leonardo.diabetes_prediction.exception.ModelDefinitionException;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class TrainModel {

    private static final Logger logger = LoggerFactory.getLogger(TrainModel.class);

    public static Trainer createTrainer(Model model, double[][] data) throws TranslateException, IOException {

        int batchSize = 256;

        System.out.println(data.length + " - " + data[0].length);



        //crea un NDManager per gestire e creare NDArray in modo efficiente
        try(NDManager manager = NDManager.newBaseManager()) {

            NDArray features = manager.create(SplitArray.getData(data)); //converte i dati delle feature in NDArray
            NDArray labels = manager.create(SplitArray.getLabels(data)); //converte i dati delle label in NDArray

            //costruisco il dataset
            ArrayDataset dataset = new ArrayDataset.Builder()
                    .setData(features) //imposto le feature
                    .optLabels(labels) //imposto le labels
                    .setSampling(batchSize, false) //imposto la batchSize e il campionamento casuale (falso in questo caso)
                    .build();

            //stampo una porzione del dataset per verifica ed esercizio
            Batch batch = dataset.getData(manager).iterator().next();
            NDArray X = batch.getData().head(); //head restituisce la prima riga, ma non lo fa
            NDArray y = batch.getLabels().head(); //head restituisce la prima riga, ma non lo fa
            System.out.println(X);
            System.out.println(y);
            batch.close(); //Chiudere!



            DefaultTrainingConfig config = new DefaultTrainingConfig(Loss.sigmoidBinaryCrossEntropyLoss())
                    .addEvaluator(new Accuracy())
                    .optDevices(manager.getEngine().getDevices(2))
                    .addTrainingListeners(TrainingListener.Defaults.logging())
                    .optOptimizer(Optimizer.adam().build());


            return model.newTrainer(config);

        } catch (Exception e) {
            throw new ModelDefinitionException("Impossibile definire il modello",e);
        }

    }

    public static void train(Trainer trainer, double[][] managedData) throws TranslateException, IOException {

        int batchSize = 128;
        int epochs = 128;

        Map<String,double[]> evaluatorMetrics = new HashMap<>();

        Map<String,double[][]> allData = SplitArray.splitValidation(managedData,0.2f);

        CustomDataset dataset = CustomDataset.builder()
                .setData(SplitArray.getData(allData.get("training")))
                .setLabels(SplitArray.getLabels(allData.get("training")))
                .setSampling(batchSize,true)
                .build();

        CustomDataset validation = CustomDataset.builder()
                .setData(SplitArray.getData(allData.get("validation")))
                .setLabels(SplitArray.getLabels(allData.get("validation")))
                .setSampling(batchSize,true)
                .build();


        trainer.initialize(new Shape(batchSize,15));
        trainer.setMetrics(new Metrics());

        EasyTrain.fit(trainer,epochs,dataset,validation);
        Metrics metrics = trainer.getMetrics();

        trainer.getEvaluators().stream()
                .forEach(evaluator -> {
                    evaluatorMetrics.put("train_epoch_" + evaluator.getName(), metrics.getMetric("train_epoch_" + evaluator.getName()).stream()
                            .mapToDouble(x -> x.getValue().doubleValue()).toArray());
                    evaluatorMetrics.put("validate_epoch_" + evaluator.getName(), metrics.getMetric("validate_epoch_" + evaluator.getName()).stream()
                            .mapToDouble(x -> x.getValue().doubleValue()).toArray());
                });


        double[] trainLoss = new double[epochs];
        double[] trainAccuracy = new double[epochs];
        double[] testAccuracy = new double[epochs];
        double[] epochCount = new double[epochs];
        for(int i = 0; i < epochCount.length; i++) {
            epochCount[i] = (i + 1);
        }

        trainLoss = evaluatorMetrics.get("train_epoch_SigmoidBinaryCrossEntropyLoss");
        trainAccuracy = evaluatorMetrics.get("train_epoch_Accuracy");
        testAccuracy = evaluatorMetrics.get("validate_epoch_Accuracy");

        System.out.println(Arrays.toString(trainLoss));
        System.out.println(Arrays.toString(trainAccuracy));
        System.out.println(Arrays.toString(testAccuracy));

        String[] lossLabel = new String[trainLoss.length + testAccuracy.length + trainAccuracy.length];

        Arrays.fill(lossLabel, 0, trainLoss.length, "test acc");
        Arrays.fill(lossLabel, trainAccuracy.length, trainLoss.length + trainAccuracy.length, "train acc");
        Arrays.fill(lossLabel, trainLoss.length + trainAccuracy.length,
                trainLoss.length + testAccuracy.length + trainAccuracy.length, "train loss");

//        Table data = Table.create("Data").addColumns(
//                DoubleColumn.create("epochCount", ArrayUtils.addAll(epochCount, ArrayUtils.addAll(epochCount, epochCount))),
//                DoubleColumn.create("loss", ArrayUtils.addAll(testAccuracy , ArrayUtils.addAll(trainAccuracy, trainLoss))),
//                StringColumn.create("lossLabel", lossLabel)
//        );



    }

}
