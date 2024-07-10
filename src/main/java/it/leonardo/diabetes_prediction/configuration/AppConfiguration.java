package it.leonardo.diabetes_prediction.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@Getter
@Setter
public class AppConfiguration {

    private final Environment environment;

    @Autowired
    public AppConfiguration(Environment environment) {
        this.environment = environment;
    }

    public String getTableName() {
        return environment.getProperty("app.datasource.table-name");
    }

    public int getBatchSize() {
        return Integer.parseInt(environment.getProperty("app.datasource.batch-size"));
    }

    public float getBalancePercentage() {
        return Float.parseFloat(environment.getProperty("app.data-analisys.unbalance-fraction"));
    }

    public Path getSaveModelPath() {
        return Path.of(environment.getProperty("models.path.save"));
    }


}
