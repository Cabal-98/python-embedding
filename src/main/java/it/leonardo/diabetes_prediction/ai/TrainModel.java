package it.leonardo.diabetes_prediction.ai;

import ai.djl.*;
import ai.djl.ndarray.types.Shape;
import ai.djl.training.*;
import ai.djl.training.evaluator.Accuracy;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import ai.djl.training.optimizer.Optimizer;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import it.leonardo.diabetes_prediction.data.datasets.CustomDataset;
import it.leonardo.diabetes_prediction.data.tools.SplitArray;
import it.leonardo.diabetes_prediction.service.DataAnalisiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.io.IOException;

@Component
@AllArgsConstructor
public class TrainModel {

    private final DataAnalisiService dataAnalisiService;

    public static Trainer createTrainer(Model model) {

        DefaultTrainingConfig config = new DefaultTrainingConfig(Loss.sigmoidBinaryCrossEntropyLoss())
                .addEvaluator(new Accuracy())
                .addTrainingListeners(TrainingListener.Defaults.logging())
                .optOptimizer(Optimizer.adam().build());

        Trainer trainer = model.newTrainer(config);
        return trainer;
    }

    public static void train(Trainer trainer, double[][] managedData) throws TranslateException, IOException {

        int batchSize = 128;
        int epochs = 128;

        trainer.initialize(new Shape(batchSize,15));

        CustomDataset dataset = CustomDataset.builder()
                .setData(SplitArray.getData(managedData))
                .setLabels(SplitArray.getLabels(managedData))
                .setSampling(batchSize,true)
                .build();

        EasyTrain.fit(trainer,epochs,dataset,null);
    }

}
