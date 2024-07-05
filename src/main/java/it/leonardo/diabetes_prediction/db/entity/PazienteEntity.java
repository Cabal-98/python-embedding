package it.leonardo.diabetes_prediction.db.entity;

import lombok.Builder;

@Builder
public record PazienteEntity(
        int age,
        boolean hypertension,
        boolean heartDisease,
        float bmi,
        float hba1cLevel,
        int bloodGlucoseLevel,
        String gender,
        String smokingHistory,
        boolean diabetes
) {

}
