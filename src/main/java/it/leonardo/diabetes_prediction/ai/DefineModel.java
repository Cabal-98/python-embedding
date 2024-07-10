package it.leonardo.diabetes_prediction.ai;

import ai.djl.*;
import ai.djl.nn.*;
import ai.djl.nn.core.*;
import ai.djl.training.initializer.NormalInitializer;
import org.springframework.stereotype.Component;

@Component
public class DefineModel {

    public static Model createModel() {

        //Application app = Application.UNDEFINED; //NON CHIARO A COSA SERVA

        long inputSize = 15;
        long outputSize = 1;

        SequentialBlock block = new SequentialBlock(); //RAPPRESENTA IL MODELLO E L'INSIEME DEI PARAMETRI - VA RIEMPITO
        //block.add(Linear.builder().setUnits(inputSize).build());
        //block.add(Activation::relu);
        block.add(Linear.builder().setUnits(64).build());
        block.add(Activation::relu);
        block.add(Linear.builder().setUnits(32).build());
        block.add(Activation::relu);
        block.add(Linear.builder().setUnits(outputSize).build());
        block.add(Activation::sigmoid);
        block.setInitializer(new NormalInitializer(), Parameter.Type.WEIGHT);

        Model model = Model.newInstance("MLP");
        model.setBlock(block);

        return model;

    }


}
