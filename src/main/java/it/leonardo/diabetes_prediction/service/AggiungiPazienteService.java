package it.leonardo.diabetes_prediction.service;

import it.leonardo.diabetes_prediction.db.dao.PazienteDAO;
import it.leonardo.diabetes_prediction.dto.PazienteDTO;
import it.leonardo.diabetes_prediction.exception.DAOException;
import it.leonardo.diabetes_prediction.mapper.PazienteMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AggiungiPazienteService {

    private static final Logger logger = LoggerFactory.getLogger(AggiungiPazienteService.class);
    private PazienteMapper pazienteMapper;
    private PazienteDAO pazienteDAO;

    public void ScriviPaziente(PazienteDTO paziente) {

        try {
            pazienteDAO.save(pazienteMapper.toEntity(paziente));
            logger.info("Caricamento paziente completato");
        } catch (Exception e) {
            throw new DAOException("Errore nel caricamento del paziente sul database");
        }

    }



}
