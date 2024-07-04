package it.leonardo.diabetes_prediction.db.dao;

import it.leonardo.diabetes_prediction.configuration.AppConfiguration;
import it.leonardo.diabetes_prediction.db.entity.PazienteEntity;
import it.leonardo.diabetes_prediction.exception.DAOException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class PazienteDAO {

    private static final Logger logger = LoggerFactory.getLogger(PazienteDAO.class);

    private final AppConfiguration appConfiguration;
    @Autowired
    public PazienteDAO(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(PazienteEntity paziente) {

        String dbName = appConfiguration.getTableName();
        String sqlQuery = String.format("""
         INSERT INTO %s
         (age,hypertension,heartdisease,bmi,hba1clevel,bloodglucoselevel,gender,smokinghistory,diabetes)
         VALUES (?,?,?,?,?,?,?,?,?)
         """, dbName);

        jdbcTemplate.update(
                sqlQuery,
                paziente.age(),
                paziente.hypertension(),
                paziente.heartDisease(),
                paziente.bmi(),
                paziente.HbA1cLevel(),
                paziente.bloodGlucoseLevel(),
                paziente.gender(),
                paziente.smokingHistory(),
                paziente.diabetes()
        );
    }

    public void saveMultipleRecords(List<PazienteEntity> pazienti) {

        String dbName = appConfiguration.getTableName();
        String sqlQuery = String.format("""
         INSERT INTO %s
         (age,hypertension,heartdisease,bmi,hba1clevel,bloodglucoselevel,gender,smokinghistory,diabetes)
         VALUES (?,?,?,?,?,?,?,?,?)
         """, dbName);

        int totalSize = pazienti.size();
        int numberOfBatches = (totalSize + appConfiguration.getBatchSize() - 1) / appConfiguration.getBatchSize();

        try {
            for(int batchIndex = 0; batchIndex < numberOfBatches; batchIndex++) {
                int fromIndex = batchIndex * appConfiguration.getBatchSize();
                int toIndex = Math.min(fromIndex + appConfiguration.getBatchSize(), totalSize);
                List<PazienteEntity> batchList = pazienti.subList(fromIndex,toIndex);

                List<Object[]> batchLines = new ArrayList<>();

                for(PazienteEntity paziente : batchList) {
                    Object[] values = new Object[]{
                            paziente.age(),
                            booleanToInt(paziente.hypertension()),
                            booleanToInt(paziente.heartDisease()),
                            paziente.bmi(),
                            paziente.HbA1cLevel(),
                            paziente.bloodGlucoseLevel(),
                            paziente.gender().toUpperCase(),
                            paziente.smokingHistory().toUpperCase(),
                            booleanToInt(paziente.diabetes())
                    };
                    batchLines.add(values);
                }

                jdbcTemplate.batchUpdate(sqlQuery, batchLines);
                logger.info("Batch " + (batchIndex+1) + " di " + numberOfBatches + "effettuato con successo.");
            }

        } catch (Exception e) {
            throw new DAOException("Errore nella batchInsert nel database");
        }

    }

    public List<PazienteEntity> findAll() {

        String dbName = appConfiguration.getTableName();
        String sqlQuery = String.format("""
        SELECT age, hypertension, heartdisease, bmi, hba1clevel, bloodglucoselevel, gender, smokinghistory, diabetes
        FROM %s
        """, dbName);

        return jdbcTemplate.query(
                sqlQuery,
                new DataClassRowMapper<>(PazienteEntity.class)
        );
    }

    public List<PazienteEntity> findSome(int limit) {

        String dbName = appConfiguration.getTableName();
        String sqlQuery = String.format("""
        SELECT age, hypertension, heartdisease, bmi, hba1clevel, bloodglucoselevel, gender, smokinghistory, diabetes
        FROM %s
        LIMIT %s
        """, dbName, limit);

        return jdbcTemplate.query(
                sqlQuery,
                new DataClassRowMapper<>(PazienteEntity.class)
        );
    }

    public Integer countData() {

        String dbName = appConfiguration.getTableName();
        String conutPazienti = String.format("""
                select count(*) from %s pd
                """, dbName);

        return jdbcTemplate.queryForObject(conutPazienti,
                Integer.class);

    }

    public Integer countDiabetics() {

        String dbName = appConfiguration.getTableName();
        String conutPazienti = String.format("""
                select count(*) from %s pd
                where diabetes = 1
                """, dbName);

        return jdbcTemplate.queryForObject(conutPazienti,
                Integer.class);

    }



    private int booleanToInt(Boolean bool) {
        if(bool) {
            return 1;
        } else {
            return 0;
        }
    }


}
