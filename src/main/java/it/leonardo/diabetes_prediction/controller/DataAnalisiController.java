package it.leonardo.diabetes_prediction.controller;

import it.leonardo.diabetes_prediction.dto.DataInfoDTO;
import it.leonardo.diabetes_prediction.dto.PazienteEncodedDTO;
import it.leonardo.diabetes_prediction.service.DataAnalisiService;
import it.leonardo.diabetes_prediction.service.DataInfoService;
import it.leonardo.diabetes_prediction.service.ScriviCSVService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/data-analisi")
@AllArgsConstructor
public class DataAnalisiController {

    private DataInfoService dataInfoService;
    private DataAnalisiService dataAnalisiService;
    private ScriviCSVService scriviCSVService;

    @GetMapping(path = "/info-dataset")
    public DataInfoDTO getInfo() {
        return dataInfoService.getInfo();
    }

    @GetMapping(path = "/dataset-analizzato")
    public double[][] dataAnalisi() {

        return dataAnalisiService.analisiDati();
    }

    @GetMapping(path = "/dataset-analizzato/csv")
    public ResponseEntity<String> dataAnalisiCSV() throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {

        String csvContent = scriviCSVService.scriviCSV(dataAnalisiService.analisiDati(true));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        String formattedDataTime = now.format(formatter);

        String filename = String.format("datiAnalizzati_DP_%s.csv",formattedDataTime);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

        return ResponseEntity.ok().headers(headers).body(csvContent);
    }




}
