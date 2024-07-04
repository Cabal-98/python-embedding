package it.leonardo.diabetes_prediction.controller;

import it.leonardo.diabetes_prediction.dto.DataInfoDTO;
import it.leonardo.diabetes_prediction.dto.PazienteEncodedDTO;
import it.leonardo.diabetes_prediction.service.DataAnalisiService;
import it.leonardo.diabetes_prediction.service.DataInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data-analisi")
@AllArgsConstructor
public class DataAnalisiController {

    private DataInfoService dataInfoService;
    private DataAnalisiService dataAnalisiService;

    @GetMapping(path = "/info-dataset")
    public DataInfoDTO getInfo() {
        return dataInfoService.getInfo();
    }

    @GetMapping(path = "/one-hot-encoder")
    public List<PazienteEncodedDTO> dataEncoder() {
        //return dataAnalisiService.analisiDati();
    }


}
