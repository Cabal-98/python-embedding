package it.leonardo.diabetes_prediction.controller;

import it.leonardo.diabetes_prediction.dto.PazienteDTO;
import it.leonardo.diabetes_prediction.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/paziente")
@AllArgsConstructor
public class PazienteController {

    private AggiungiPazienteDaCSVService aggiungiPazienteDaCSVService;
    private AggiungiPazienteService aggiungiPazienteService;
    private LeggiPazientiService leggiPazientiService;
    private DataInfoService dataInfoService;
    private ScriviCSVService scriviCSVService;

    @GetMapping("/hello-world")
    public String helloWorld() {
        String s = """
                Benvenuto all'applicazione diagnostica: AI-DIABETES-DIAGNOSIS
                Il dataset è stato ottenuto da www.kaggle.com
                L'analisi e la costruzione del modello sono a cura di Simone Feliziani
                """;
        return s;
    }

    @GetMapping(path = "/leggi-dataset")
    public List<PazienteDTO> getDataset() {
        return leggiPazientiService.LeggiDataset();
    }

    @GetMapping(path = "/leggi-dataset/csv")
    public ResponseEntity<String> getDatasetCSV() {

        String csvContent = scriviCSVService.scriviCSV(leggiPazientiService.LeggiDataset());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDateTime = now.format(formatter);

        String filename = String.format("datiDiabetesPrediction_%s.csv", formattedDateTime);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

        return ResponseEntity.ok().headers(headers).body(csvContent);
    }

    @GetMapping(path = "/leggi-dataset/{limit}")
    public List<PazienteDTO> getDatasetPortion(@PathVariable(name="limit") int limit) {
        return leggiPazientiService.LeggiDatasetPortion(limit);
    }

    @GetMapping(path = "/leggi-dataset/csv/{limit}")
    public ResponseEntity<String> getDatasetPortionCSV(@PathVariable(name="limit") int limit) {

        String csvContent = scriviCSVService.scriviCSV(leggiPazientiService.LeggiDatasetPortion(limit));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDateTime = now.format(formatter);

        String filename = String.format("datiDiabetesPrediction_%s.csv", formattedDateTime);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

        return ResponseEntity.ok().headers(headers).body(csvContent);
    }

    @PostMapping(path = "/carica-csv", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void caricaCSV(@RequestPart(value = "file") MultipartFile file) throws IOException {
        aggiungiPazienteDaCSVService.ScriviDaCSV(file);
    }

    @PostMapping(path = "/add")
    public String creaPaziente(@RequestBody PazienteDTO paziente) {

        aggiungiPazienteService.ScriviPaziente(paziente);
        return "Utente creato con successo";
    }

}
