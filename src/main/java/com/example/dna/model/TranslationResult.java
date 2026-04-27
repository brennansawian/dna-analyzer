package com.example.dna.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class TranslationResult {

    private String proteinSequence;
    private Map<String, String> codonExplanations = new LinkedHashMap<>();

    public String getProteinSequence() {
        return proteinSequence;
    }

    public void setProteinSequence(String proteinSequence) {
        this.proteinSequence = proteinSequence;
    }

    public Map<String, String> getCodonExplanations() {
        return codonExplanations;
    }

    public void setCodonExplanations(Map<String, String> codonExplanations) {
        this.codonExplanations = codonExplanations;
    }
}
