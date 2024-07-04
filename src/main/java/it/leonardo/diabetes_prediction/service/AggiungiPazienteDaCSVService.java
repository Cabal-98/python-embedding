package it.leonardo.diabetes_prediction.service;

import it.leonardo.diabetes_prediction.db.dao.PazienteDAO;
import it.leonardo.diabetes_prediction.dto.PazienteDTO;
import it.leonardo.diabetes_prediction.mapper.PazienteMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class AggiungiPazienteDaCSVService {

    private static final Logger logger = LoggerFactory.getLogger(AggiungiPazienteDaCSVService.class);

    private LeggiCSVService leggiCSVService;
    private PazienteMapper pazienteMapper;
    private PazienteDAO pazienteDAO;

    public void ScriviDaCSV(MultipartFile file) throws IOException {

        List<PazienteDTO> pazienti = leggiCSVService.leggiCSV(file);
        pazienteDAO.saveMultipleRecords(pazienteMapper.toEntity(pazienti));
        logger.info("Caricamento CSV completato");

    }



}
