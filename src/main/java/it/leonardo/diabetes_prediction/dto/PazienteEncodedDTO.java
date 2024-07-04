package it.leonardo.diabetes_prediction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PazienteEncodedDTO {
    private int age;
    private boolean hypertension;
    private boolean heartDisease;
    private float bmi;
    private float hba1cLevel;
    private int bloodGlucoseLevel;
    private boolean male;
    private boolean female;
    private boolean other;
    private boolean never;
    private boolean noInfo;
    private boolean current;
    private boolean former;
    private boolean ever;
    private boolean notCurrent;
    private boolean diabetes;

    // Costruttore con tutti i parametri
    public PazienteEncodedDTO(int age, boolean hypertension, boolean heartDisease, float bmi, float hba1cLevel, int bloodGlucoseLevel, boolean male, boolean female, boolean other, boolean never, boolean noInfo, boolean current, boolean former, boolean ever, boolean notCurrent, boolean diabetes) {
        this.age = age;
        this.hypertension = hypertension;
        this.heartDisease = heartDisease;
        this.bmi = bmi;
        this.hba1cLevel = hba1cLevel;
        this.bloodGlucoseLevel = bloodGlucoseLevel;
        this.male = male;
        this.female = female;
        this.other = other;
        this.never = never;
        this.noInfo = noInfo;
        this.current = current;
        this.former = former;
        this.ever = ever;
        this.notCurrent = notCurrent;
        this.diabetes = diabetes;
    }

    public PazienteEncodedDTO() {
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

    public float getHba1cLevel() {
        return hba1cLevel;
    }

    public void setHba1cLevel(float hba1cLevel) {
        this.hba1cLevel = hba1cLevel;
    }

    public int getBloodGlucoseLevel() {
        return bloodGlucoseLevel;
    }

    public void setBloodGlucoseLevel(int bloodGlucoseLevel) {
        this.bloodGlucoseLevel = bloodGlucoseLevel;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public boolean isNever() {
        return never;
    }

    public void setNever(boolean never) {
        this.never = never;
    }

    public boolean isNoInfo() {
        return noInfo;
    }

    public void setNoInfo(boolean noInfo) {
        this.noInfo = noInfo;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public boolean isFormer() {
        return former;
    }

    public void setFormer(boolean former) {
        this.former = former;
    }

    public boolean isEver() {
        return ever;
    }

    public void setEver(boolean ever) {
        this.ever = ever;
    }

    public boolean isNotCurrent() {
        return notCurrent;
    }

    public void setNotCurrent(boolean notCurrent) {
        this.notCurrent = notCurrent;
    }

    public boolean isDiabetes() {
        return diabetes;
    }

    public void setDiabetes(boolean diabetes) {
        this.diabetes = diabetes;
    }
}

