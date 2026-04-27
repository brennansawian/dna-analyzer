package com.example.codonmapping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "codon_mapping")
public class CodonMapping {

    @Id
    private String codon; // e.g. "AUG"

    private String aminoAcid; // e.g. "Methionine"

    private String onelettercode;

    private String threelettercode;

    private String isstart;

    private String isstop;

    // Constructors
    public CodonMapping() {
    }

    public CodonMapping(String codon, String aminoAcid) {
        this.codon = codon;
        this.aminoAcid = aminoAcid;
    }

    // Getters and Setters

    public String getCodon() {
        return codon;
    }

    public String getOnelettercode() {
        return onelettercode;
    }

    public void setOnelettercode(String onelettercode) {
        this.onelettercode = onelettercode;
    }

    public String getThreelettercode() {
        return threelettercode;
    }

    public void setThreelettercode(String threelettercode) {
        this.threelettercode = threelettercode;
    }

    public String getIsstart() {
        return isstart;
    }

    public void setIsstart(String isstart) {
        this.isstart = isstart;
    }

    public String getIsstop() {
        return isstop;
    }

    public void setIsstop(String isstop) {
        this.isstop = isstop;
    }

    public void setCodon(String codon) {
        this.codon = codon;
    }

    public String getAminoAcid() {
        return aminoAcid;
    }

    public void setAminoAcid(String aminoAcid) {
        this.aminoAcid = aminoAcid;
    }
}
