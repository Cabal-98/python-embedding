package it.leonardo.diabetes_prediction.service;

import it.leonardo.diabetes_prediction.configuration.AppConfiguration;
import it.leonardo.diabetes_prediction.data.converter.ClassToDoubleArrayService;
import it.leonardo.diabetes_prediction.data.converter.ClassToTensorService;
import it.leonardo.diabetes_prediction.data.tools.*;
import it.leonardo.diabetes_prediction.db.dao.DataDAO;
import it.leonardo.diabetes_prediction.dto.PazienteDTO;
import it.leonardo.diabetes_prediction.dto.PazienteEncodedDTO;
import it.leonardo.diabetes_prediction.mapper.PazienteMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DataAnalisiService {

    private final AppConfiguration appConfiguration;
    private final DataInfoService dataInfoService;

    private static final Logger logger = LoggerFactory.getLogger(DataAnalisiService.class);

    private final PazienteMapper pazienteMapper;
    private final DataDAO dataDAO;
    private final ClassToTensorService classToTensorService;
    private final ClassToDoubleArrayService classToDoubleArrayService;
    private final OneHotEncoder oneHotEncoder;
    private final GetFieldsIndexMap getFieldsIndexMap;
    private final Smoter smoter;
    private final DataManager dataManager;

    public double[][] analisiDatiBalanced() {

        List<PazienteDTO> shuffeledBalancedData = pazienteMapper.toDto(dataDAO.getShuffledBalancedDataset(calcolaNumeroDiabeteNegativi()));
        logger.info("Estrazione dati completata.");
        List<PazienteEncodedDTO> datiAnalizzati = oneHotEncoder.arrayColumnEncoder(PazienteEncodedDTO.class, shuffeledBalancedData, Arrays.asList("gender","smokingHistory"));
        logger.info("Encoding completato.");

        Map<String, Integer> mappaIndici = getFieldsIndexMap.getIndexMap(datiAnalizzati.get(0).getClass());
        double[][] dataset = classToDoubleArrayService.listToDoubleArray(datiAnalizzati);
        dataset = RobustScaler.robustScaleMatrix(dataset, mappaIndici, Arrays.asList("age","bmi","hba1cLevel","bloodGlucoseLevel"));

        return dataset;
    }

    public double[][] analisiDatiPortion(int estrazioni) {

        List<PazienteDTO> shuffeledBalancedData = pazienteMapper.toDto(dataDAO.getShuffledBalancedDataset(estrazioni));
        logger.info("Estrazione dati completata.");
        List<PazienteEncodedDTO> datiAnalizzati = oneHotEncoder.arrayColumnEncoder(PazienteEncodedDTO.class, shuffeledBalancedData, Arrays.asList("gender","smokingHistory"));
        logger.info("Encoding completato.");

        Map<String, Integer> mappaIndici = getFieldsIndexMap.getIndexMap(datiAnalizzati.get(0).getClass());
        double[][] dataset = classToDoubleArrayService.listToDoubleArray(datiAnalizzati);
        dataset = RobustScaler.robustScaleMatrix(dataset, mappaIndici, Arrays.asList("age","bmi","hba1cLevel","bloodGlucoseLevel"));

        return dataset;
    }

    public double[][] analisiDati(double smoteValue) {

        List<PazienteDTO> datiPuri = pazienteMapper.toDto(dataDAO.getDataset());
        logger.info("Estrazione dati dal db completata");
        List<PazienteEncodedDTO> encodedList = oneHotEncoder.arrayColumnEncoder(PazienteEncodedDTO.class, datiPuri , Arrays.asList("gender","smokingHistory"));
        logger.info("Encoding completato");
        double[][] dataset = classToDoubleArrayService.listToDoubleArray(encodedList);
        double[][] smotedDataset = smoter.smote(dataset, smoteValue);
        logger.info("Smoting completato");
        Map<String, Integer> mappaIndici = getFieldsIndexMap.getIndexMap(encodedList.getFirst().getClass());
        double[][] scaledDataset = RobustScaler.robustScaleMatrix(smotedDataset, mappaIndici, Arrays.asList("age","bmi","hba1cLevel","bloodGlucoseLevel"));
        logger.info("Robust Scaling completato");

        return dataManager.balance(scaledDataset,0.55);
    }


    private int calcolaNumeroDiabeteNegativi() {
        int diabetici = dataInfoService.getInfo().getNumeroDiabetici();
        return (int) (diabetici * appConfiguration.getBalancePercentage() / (1-appConfiguration.getBalancePercentage()));
    }


}
