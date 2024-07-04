package it.leonardo.diabetes_prediction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PazienteDTO {
    private int age;
    private boolean hypertension;
    private boolean heartDisease;
    private float bmi;
    private float HbA1cLevel;
    private int bloodGlucoseLevel;
    private String gender;
    private String smokingHistory;
    private boolean diabetes;

    // Costruttore con tutti i parametri
    public PazienteDTO(int age, boolean hypertension, boolean heartDisease, float bmi, float HbA1cLevel, int bloodGlucoseLevel, String gender, String smokingHistory, boolean diabetes) {
        this.age = age;
        this.hypertension = hypertension;
        this.heartDisease = heartDisease;
        this.bmi = bmi;
        this.HbA1cLevel = HbA1cLevel;
        this.bloodGlucoseLevel = bloodGlucoseLevel;
        this.gender = gender;
        this.smokingHistory = smokingHistory;
        this.diabetes = diabetes;
    }

    public PazienteDTO() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isHypertension() {
        return hypertension;
    }

    public void setHypertension(boolean hypertension) {
        this.hypertension = hypertension;
    }

    public boolean isHeartDisease() {
        return heartDisease;
    }

    public void setHeartDisease(boolean heartDisease) {
        this.heartDisease = heartDisease;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public float getHbA1cLevel() {
        return HbA1cLevel;
    }

    public void setHbA1cLevel(float hbA1cLevel) {
        HbA1cLevel = hbA1cLevel;
    }

    public int getBloodGlucoseLevel() {
        return bloodGlucoseLevel;
    }

    public void setBloodGlucoseLevel(int bloodGlucoseLevel) {
        this.bloodGlucoseLevel = bloodGlucoseLevel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSmokingHistory() {
        return smokingHistory;
    }

    public void setSmokingHistory(String smokingHistory) {
        this.smokingHistory = smokingHistory;
    }

    public boolean isDiabetes() {
        return diabetes;
    }

    public void setDiabetes(boolean diabetes) {
        this.diabetes = diabetes;
    }
}
