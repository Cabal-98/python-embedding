package it.leonardo.diabetes_prediction.service;

import it.leonardo.diabetes_prediction.db.dao.PazienteDAO;
import it.leonardo.diabetes_prediction.dto.PazienteDTO;
import it.leonardo.diabetes_prediction.exception.DAOException;
import it.leonardo.diabetes_prediction.mapper.PazienteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LeggiPazientiService {

    private PazienteMapper pazienteMapper;
    private PazienteDAO pazienteDAO;

    public List<PazienteDTO> LeggiDataset() {

        try {
            return pazienteMapper.toDto(pazienteDAO.findAll());
        } catch (Exception e) {
            throw new DAOException("Errore nella lettura dal database");
        }

    }

    public List<PazienteDTO> LeggiDatasetPortion(int limit) {

        try {
            return pazienteMapper.toDto(pazienteDAO.findSome(limit));
        } catch (Exception e) {
            throw new DAOException("Errore nella lettura dal database");
        }

    }


}
