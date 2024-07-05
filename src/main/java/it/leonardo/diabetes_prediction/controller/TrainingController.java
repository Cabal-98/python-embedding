package it.leonardo.diabetes_prediction.controller;

import ai.djl.Model;
import ai.djl.training.Trainer;
import ai.djl.translate.TranslateException;
import it.leonardo.diabetes_prediction.ai.DefineModel;
import it.leonardo.diabetes_prediction.ai.TrainModel;
import it.leonardo.diabetes_prediction.service.DataAnalisiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/model")
@AllArgsConstructor
public class TrainingController {

    private DataAnalisiService dataAnalisiService;
    private DefineModel defineModel;
    private TrainModel trainModel;

    @GetMapping(path = "/training")
    public String modelTraining() throws TranslateException, IOException {

        Model modello = defineModel.createModel();
        Trainer trainer = trainModel.createTrainer(modello);
        trainModel.train(trainer, dataAnalisiService.analisiDati());

        return """
                Addestramento Completato
                """;
    }


}
