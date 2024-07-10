package it.leonardo.diabetes_prediction.db.dao;

import it.leonardo.diabetes_prediction.configuration.AppConfiguration;
import it.leonardo.diabetes_prediction.db.entity.PazienteEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class DataDAO {

    private static final Logger logger = LoggerFactory.getLogger(DataDAO.class);

    private final AppConfiguration appConfiguration;
    @Autowired
    public DataDAO(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @Autowired
    private JdbcTemplate template;

    public List<PazienteEntity> getShuffledBalancedDataset(int numeroDatiEstratti) {

        String dbName = appConfiguration.getTableName();
        String sqlQuery = String.format("""
                SELECT *
                FROM (
                    SELECT *
                    FROM %s
                    WHERE diabetes = 1
                
                    UNION ALL
                
                    SELECT *
                    FROM %s
                    WHERE diabetes = 1 IS NOT TRUE
                    ORDER BY RAND()
                    LIMIT %s
                ) AS combined_results
                ORDER BY RAND()
                """, dbName, dbName, numeroDatiEstratti);

        return template.query(
                sqlQuery,
                new DataClassRowMapper<>(PazienteEntity.class)
        );
    }

    public List<PazienteEntity> getDataset() {

        String dbName = appConfiguration.getTableName();
        String sqlQuery = String.format("""
                SELECT *
                FROM %s
                """, dbName);

        return template.query(
                sqlQuery,
                new DataClassRowMapper<>(PazienteEntity.class)
        );
    }

}
