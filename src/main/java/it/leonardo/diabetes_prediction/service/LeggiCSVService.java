package it.leonardo.diabetes_prediction.service;

import it.leonardo.diabetes_prediction.dto.PazienteDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LeggiCSVService {

    private static final Logger logger = LoggerFactory.getLogger(LeggiCSVService.class);

    public List<PazienteDTO> leggiCSV (MultipartFile file) throws IOException {


        InputStream inputStream = file.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Boolean firstLine = true;

        try {
            String riga;
            List<PazienteDTO> data = new ArrayList<>();
            while ((riga = reader.readLine()) != null) {
                String[] istanza = riga.split(",");

                if (firstLine) {
                    // Se è la prima riga, la saltiamo
                    firstLine = false;
                    continue;
                }


                // Costruzione del record PazienteDTO
                PazienteDTO paziente = PazienteDTO.builder()
                        .age((int) Float.parseFloat(istanza[1]))
                        .hypertension(convertToBoolean(istanza[2]))
                        .heartDisease(convertToBoolean(istanza[3]))
                        .bmi(Float.parseFloat(istanza[5]))
                        .HbA1cLevel(Float.parseFloat(istanza[6]))
                        .bloodGlucoseLevel(Integer.parseInt(istanza[7]))
                        .gender(istanza[0])
                        .smokingHistory(istanza[4])
                        .diabetes(convertToBoolean(istanza[8]))
                        .build();

                data.add(paziente);
            }
            reader.close();
            return data;
        } catch (Exception e) {
            throw new ServerException("Errore durante il parsing del file CSV");
        }

    }

    private boolean convertToBoolean(String value) {
        return "1".equals(value);
    }

}
