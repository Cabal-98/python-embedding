package it.leonardo.diabetes_prediction.service;

import it.leonardo.diabetes_prediction.db.dao.PazienteDAO;
import it.leonardo.diabetes_prediction.db.entity.DataInfoEntity;
import it.leonardo.diabetes_prediction.dto.DataInfoDTO;
import it.leonardo.diabetes_prediction.mapper.DataInfoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DataInfoService {

    private DataInfoMapper dataInfoMapper;
    private PazienteDAO pazienteDao;

    public DataInfoDTO getInfo() {

        DataInfoEntity dataInfo = new DataInfoEntity();

        dataInfo.setNumeroDati(pazienteDao.countData());
        dataInfo.setNumeroDiabetici(pazienteDao.countDiabetics());
        dataInfo.setEsempioDati(pazienteDao.findSome(1).get(0));
        dataInfo.setPercentualeDiabetici(calcolaPercentualeDiabetici(dataInfo.getNumeroDati(),dataInfo.getNumeroDiabetici()));

        return dataInfoMapper.toDto(dataInfo);
    }

    private float calcolaPercentualeDiabetici(int numeroDati, int numeroDiabetici) {
        return (float) numeroDiabetici / numeroDati;
    }

}
