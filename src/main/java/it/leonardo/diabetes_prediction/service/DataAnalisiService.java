package it.leonardo.diabetes_prediction.service;

import it.leonardo.diabetes_prediction.configuration.AppConfiguration;
import it.leonardo.diabetes_prediction.data.converter.ClassToDoubleArrayService;
import it.leonardo.diabetes_prediction.data.converter.ClassToTensorService;
import it.leonardo.diabetes_prediction.data.tools.GetFieldsIndexMap;
import it.leonardo.diabetes_prediction.data.tools.OneHotEncoder;
import it.leonardo.diabetes_prediction.data.tools.RobustScaler;
import it.leonardo.diabetes_prediction.db.dao.DataDAO;
import it.leonardo.diabetes_prediction.dto.PazienteDTO;
import it.leonardo.diabetes_prediction.dto.PazienteEncodedDTO;
import it.leonardo.diabetes_prediction.mapper.PazienteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.tensorflow.Tensor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.tensorflow.Tensors.create;

@Service
@AllArgsConstructor
public class DataAnalisiService {

    private final AppConfiguration appConfiguration;
    private final DataInfoService dataInfoService;

//    @Autowired
//    public DataAnalisiService(AppConfiguration appConfiguration) {
//        this.appConfiguration = appConfiguration;
//    }

    private final PazienteMapper pazienteMapper;
    private final DataDAO dataDAO;
    private final ClassToTensorService classToTensorService;
    private final ClassToDoubleArrayService classToDoubleArrayService;
    private final OneHotEncoder oneHotEncoder;
    private final GetFieldsIndexMap getFieldsIndexMap;

    public Tensor<Double> analisiDati() {

        List<PazienteDTO> shuffeledBalancedData = pazienteMapper.toDto(dataDAO.getShuffledBalancedDataset(calcolaNumeroDiabeteNegativi()));
        List<PazienteEncodedDTO> datiAnalizzati = oneHotEncoder.arrayColumnEncoder(PazienteEncodedDTO.class, shuffeledBalancedData, Arrays.asList("gender","smokingHistory"));

        Map<String, Integer> mappaIndici = getFieldsIndexMap.getIndexMap(datiAnalizzati.getClass());
        double[][] dataset = classToDoubleArrayService.listToDoubleArray(datiAnalizzati);
        dataset = RobustScaler.robustScaleMatrix(dataset, mappaIndici, Arrays.asList("age","bmi","hba1cLevel","bloodGlucoseLevel"));

        return create(dataset);
    }

    private int calcolaNumeroDiabeteNegativi() {
        int diabetici = dataInfoService.getInfo().getNumeroDiabetici();
        return (int) (diabetici * appConfiguration.getBalancePercentage() / (1-appConfiguration.getBalancePercentage()));
    }

}
