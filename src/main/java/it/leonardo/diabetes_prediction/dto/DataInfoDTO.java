package it.leonardo.diabetes_prediction.dto;

import java.util.Arrays;
import java.util.List;

public class DataInfoDTO {

    private List<String> nomiColonne = Arrays.asList("Age","Hypertension","HeartDisease","bmi","HbA1cLevel","BloodGlucoseLevel","Gender","SmokingHistory","Diabetes");
    private List<String> tipiColonne = Arrays.asList("int","boolean","boolean","float","float","int","String","String","boolean");
    private Integer numeroDati;
    private Integer numeroDiabetici;
    private float percentualeDiabetici;
    private PazienteDTO esempioDati;

    public List<String> getNomiColonne() {
        return nomiColonne;
    }

    public void setNomiColonne(List<String> nomiColonne) {
        this.nomiColonne = nomiColonne;
    }

    public List<String> getTipiColonne() {
        return tipiColonne;
    }

    public void setTipiColonne(List<String> tipiColonne) {
        this.tipiColonne = tipiColonne;
    }

    public Integer getNumeroDati() {
        return numeroDati;
    }

    public void setNumeroDati(Integer numeroDati) {
        this.numeroDati = numeroDati;
    }

    public Integer getNumeroDiabetici() {
        return numeroDiabetici;
    }

    public void setNumeroDiabetici(Integer numeroDiabetici) {
        this.numeroDiabetici = numeroDiabetici;
    }

    public float getPercentualeDiabetici() {
        return percentualeDiabetici;
    }

    public void setPercentualeDiabetici(float percentualeDiabetici) {
        this.percentualeDiabetici = percentualeDiabetici;
    }

    public PazienteDTO getEsempioDati() {
        return esempioDati;
    }

    public void setEsempioDati(PazienteDTO esempioDati) {
        this.esempioDati = esempioDati;
    }
}
