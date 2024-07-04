package it.leonardo.diabetes_prediction.db.entity;

import java.util.Arrays;
import java.util.List;

public class DataInfoEntity {

    private final List<String> nomiColonne = Arrays.asList("Age","Hypertension","HeartDisease","bmi","HbA1cLevel","BloodGlucoseLevel","Gender","SmokingHistory","Diabetes");
    private final List<String> tipiColonne = Arrays.asList("int","boolean","boolean","float","float","int","String","String","boolean");
    private Integer numeroDati;
    private Integer numeroDiabetici;
    private float percentualeDiabetici;
    private PazienteEntity esempioDati;

    public Integer getNumeroDati() {
        return numeroDati;
    }

    public Integer getNumeroDiabetici() {
        return numeroDiabetici;
    }

    public float getPercentualeDiabetici() {
        return percentualeDiabetici;
    }

    public PazienteEntity getEsempioDati() {
        return esempioDati;
    }

    public void setNumeroDati(Integer numeroDati) {
        this.numeroDati = numeroDati;
    }

    public void setNumeroDiabetici(Integer numeroDiabetici) {
        this.numeroDiabetici = numeroDiabetici;
    }

    public void setPercentualeDiabetici(float percentualeDiabetici) {
        this.percentualeDiabetici = percentualeDiabetici;
    }

    public void setEsempioDati(PazienteEntity esempioDati) {
        this.esempioDati = esempioDati;
    }
}
