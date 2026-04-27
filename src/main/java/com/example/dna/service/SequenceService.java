package com.example.dna.service;

import com.example.codonknowledge.CodonKnowledge;
import com.example.codonmapping.model.CodonMapping;
import com.example.codonmapping.repository.CodonMappingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Service
public class SequenceService {

    @Autowired
    private final CodonMappingRepo codonRepo;

    public SequenceService(CodonMappingRepo codonRepo) {
        this.codonRepo = codonRepo;
    }

    public String analyze(String dna, String type) {
        if (dna == null || dna.isEmpty()) {
            return "No DNA sequence provided.";
        }

        String mRNA = transcribeToMRNA(dna);
        String protein = translateToProtein(mRNA);

        return """
                <b>Input DNA:</b> %s<br>
                <b>mRNA Sequence:</b> %s<br>
                <b>Protein:</b> %s
                """.formatted(dna, mRNA, protein);
    }

    public String transcribeToMRNA(String dna) {
        return dna.toUpperCase()
                .replace('T', 'U')
                .replace('A', 'A')
                .replace('C', 'C')
                .replace('G', 'G');
    }

    public static class CodingRegionResult {
        String codingSequence;
        String nonCodingSequence;
        int codingCount;
        int nonCodingCount;
        double codingPercentage;
        double nonCodingPercentage;

        public CodingRegionResult(String codingSequence, String nonCodingSequence,
                int codingCount, int nonCodingCount,
                double codingPercentage, double nonCodingPercentage) {

            this.codingSequence = codingSequence;
            this.nonCodingSequence = nonCodingSequence;
            this.codingCount = codingCount;
            this.nonCodingCount = nonCodingCount;
            this.codingPercentage = codingPercentage;
            this.nonCodingPercentage = nonCodingPercentage;
        }

        public String getCodingSequence() {
            return codingSequence;
        }

        public String getNonCodingSequence() {
            return nonCodingSequence;
        }

        public int getCodingCount() {
            return codingCount;
        }

        public int getNonCodingCount() {
            return nonCodingCount;
        }

        public double getCodingPercentage() {
            return codingPercentage;
        }

        public double getNonCodingPercentage() {
            return nonCodingPercentage;
        }
    }

    // ========= CODING REGION EXTRACTION =========
    public CodingRegionResult extractCodingRegion(String mRNA) {

        StringBuilder coding = new StringBuilder();
        boolean[] isCoding = new boolean[mRNA.length()];
        String[] stops = { "UAA", "UAG", "UGA" };

        for (int i = 0; i <= mRNA.length() - 3; i++) {

            // detect start codon
            if (mRNA.substring(i, i + 3).equals("AUG")) {

                // walk in-frame from this AUG
                for (int j = i; j <= mRNA.length() - 3; j += 3) {
                    String codon = mRNA.substring(j, j + 3);

                    // mark bases as coding
                    isCoding[j] = isCoding[j + 1] = isCoding[j + 2] = true;

                    // stop codon ends ORF
                    if (codon.equals("UAA") || codon.equals("UAG") || codon.equals("UGA")) {
                        break;
                    }
                }
            }
        }

        StringBuilder nonCoding = new StringBuilder();

        for (int i = 0; i < mRNA.length(); i++) {
            if (isCoding[i]) {
                coding.append(mRNA.charAt(i));
            } else {
                nonCoding.append(mRNA.charAt(i));
            }
        }

        int codingCount = coding.length();
        int nonCodingCount = nonCoding.length();
        double total = codingCount + nonCodingCount;

        return new CodingRegionResult(
                coding.toString(),
                nonCoding.toString(),
                codingCount,
                nonCodingCount,
                (codingCount / total) * 100.0,
                (nonCodingCount / total) * 100.0);
    }

    public class mRNATranslator {

        public static class TranslationResult {
            String proteinSequence;
            int codingBases;
            int nonCodingBases;
            double codingPercentage;
            double nonCodingPercentage;

            public TranslationResult(String proteinSequence, int codingBases, int nonCodingBases,
                    double codingPercentage, double nonCodingPercentage) {
                this.proteinSequence = proteinSequence;
                this.codingBases = codingBases;
                this.nonCodingBases = nonCodingBases;
                this.codingPercentage = codingPercentage;
                this.nonCodingPercentage = nonCodingPercentage;
            }

            public String getProteinSequence() {
                return proteinSequence;
            }

            public int getCodingBases() {
                return codingBases;
            }

            public int getNonCodingBases() {
                return nonCodingBases;
            }

            public double getCodingPercentage() {
                return codingPercentage;
            }

            public double getNonCodingPercentage() {
                return nonCodingPercentage;
            }
        }

        // ========= DETAILED TRANSLATION (with stats) =========
        public TranslationResult translateToProtein(String mRNA) {
            StringBuilder protein = new StringBuilder();
            boolean translating = false;
            boolean frameLocked = false;

            int codingBases = 0;
            int nonCodingBases = 0;
            int totalBases = mRNA.length();

            for (int i = 0; i <= mRNA.length() - 3;) {
                String codon = mRNA.substring(i, i + 3);
                CodonMapping mapping = codonRepo.findByCodon(codon);

                if (mapping == null) {
                    i += frameLocked ? 3 : 1;
                    continue;
                }

                String aminoAcid = mapping.getAminoAcid();

                if (!translating) {

                    if (codon.equals("AUG")) {
                        translating = true;
                        frameLocked = true;
                        codingBases += 3;
                        protein.append("Methionine-");
                        i += 3;
                    } else {
                        nonCodingBases += 3;
                        i += frameLocked ? 3 : 1;
                    }
                } else {

                    codingBases += 3;

                    if (aminoAcid.equalsIgnoreCase("Stop")) {
                        protein.append("-");
                        translating = false;
                        frameLocked = false;
                        i += 3;
                    } else {
                        protein.append(aminoAcid).append("-");
                        i += 3;
                    }
                }
            }

            if (!translating && mRNA.length() % 3 != 0) {
                nonCodingBases += mRNA.length() % 3;
            }

            double codingPercentage = (codingBases / (double) totalBases) * 100.0;
            double nonCodingPercentage = (nonCodingBases / (double) totalBases) * 100.0;

            return new TranslationResult(
                    protein.toString(),
                    codingBases,
                    nonCodingBases,
                    codingPercentage,
                    nonCodingPercentage);
        }
    }

    // ========= SIMPLE TRANSLATION (string only) =========

    public String translateToProtein(String mRNA) {
        StringBuilder protein = new StringBuilder();
        boolean translating = false;
        boolean frameLocked = false;

        for (int i = 0; i <= mRNA.length() - 3;) {
            String codon = mRNA.substring(i, i + 3);
            CodonMapping mapping = codonRepo.findByCodon(codon);

            if (mapping == null) {
                i += frameLocked ? 3 : 1;
                continue;
            }

            String aminoAcid = mapping.getAminoAcid();

            if (!translating) {
                if (codon.equals("AUG")) {
                    translating = true;
                    frameLocked = true;
                    protein.append("Methionine-");
                    i += 3;
                } else {
                    i += frameLocked ? 3 : 1;
                }
            } else {
                if (aminoAcid.equalsIgnoreCase("Stop")) {
                    protein.append("-");
                    translating = false;
                    i += 3;
                } else {
                    protein.append(aminoAcid).append("-");
                    i += 3;
                }
            }
        }

        if (protein.length() > 0 && protein.charAt(protein.length() - 1) == '-') {
            protein.setLength(protein.length() - 1);
        }

        return protein.toString();
    }

    // ========= AI / KNOWLEDGE PART =========

    public static class KnowledgeResult {
        private String proteinSequence;
        private Map<String, String> codonExplanations = new LinkedHashMap<>();
        private Map<String, Integer> codonCounts = new LinkedHashMap<>();
        private int maxCodonCount;

        public String getProteinSequence() {
            return proteinSequence;
        }

        public void setProteinSequence(String proteinSequence) {
            this.proteinSequence = proteinSequence;
        }

        public Map<String, String> getCodonExplanations() {
            return codonExplanations;
        }

        public Map<String, Integer> getCodonCounts() {
            return codonCounts;
        }

        public int getMaxCodonCount() {
            return maxCodonCount;
        }

        public void setMaxCodonCount(int maxCodonCount) {
            this.maxCodonCount = maxCodonCount;
        }

        public void setCodonExplanations(Map<String, String> codonExplanations) {
            this.codonExplanations = codonExplanations;
        }
    }

    private void addCodingCodon(String codon, KnowledgeResult result) {
        String explanation = CodonKnowledge.getExplanation(codon);
        result.getCodonExplanations().put(codon, explanation);
        result.getCodonCounts().merge(codon, 1, Integer::sum);
    }

    // UI helper class
    public static class CodonAminoAcidUI {
        private String codon;
        private String aminoAcid;
        private String explanation;
        private int count;

        public CodonAminoAcidUI(String codon, String aminoAcid,
                String explanation, int count) {
            this.codon = codon;
            this.aminoAcid = aminoAcid;
            this.explanation = explanation;
            this.count = count;
        }

        public String getCodon() {
            return codon;
        }

        public String getAminoAcid() {
            return aminoAcid;
        }

        public String getExplanation() {
            return explanation;
        }

        public int getCount() {
            return count;
        }
    }

    public KnowledgeResult translateToProteinWithKnowledge(String mRNA) {
        KnowledgeResult result = new KnowledgeResult();

        StringBuilder protein = new StringBuilder();
        boolean translating = false;
        boolean frameLocked = false;

        for (int i = 0; i <= mRNA.length() - 3;) {
            String codon = mRNA.substring(i, i + 3);
            CodonMapping mapping = codonRepo.findByCodon(codon);

            if (mapping == null) {
                i += frameLocked ? 3 : 1;
                continue;
            }

            String aminoAcid = mapping.getAminoAcid();

            if (!translating) {
                if (codon.equals("AUG")) {
                    translating = true;
                    frameLocked = true;

                    addCodingCodon(codon, result);

                    protein.append("Methionine-");
                    i += 3;
                } else {
                    i += frameLocked ? 3 : 1;
                }
            } else {
                addCodingCodon(codon, result);

                if (aminoAcid.equalsIgnoreCase("Stop")) {
                    protein.append("-");
                    translating = false;
                    frameLocked = false;
                    i += 3;
                } else {
                    protein.append(aminoAcid).append("-");
                    i += 3;
                }
            }
        }

        if (protein.length() > 0 && protein.charAt(protein.length() - 1) == '-') {
            protein.setLength(protein.length() - 1);
        }

        result.setProteinSequence(protein.toString());

        int max = result.getCodonCounts().values()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
        result.setMaxCodonCount(max);
        Map<String, String> expl = result.getCodonExplanations();
        Map<String, Integer> counts = result.getCodonCounts();

        // get list of codons to sort
        List<String> codons = new ArrayList<>(expl.keySet());

        // sort by count descending
        codons.sort((c1, c2) -> Integer.compare(
                counts.getOrDefault(c2, 0),
                counts.getOrDefault(c1, 0)));

        // build a new LinkedHashMap in sorted order
        Map<String, String> sortedExpl = new LinkedHashMap<>();
        for (String codon : codons) {
            sortedExpl.put(codon, expl.get(codon));
        }

        // replace explanations map with the sorted one
        result.setCodonExplanations(sortedExpl);

        return result;
    }

    public List<CodonAminoAcidUI> buildCodonAminoAcidUI(KnowledgeResult result) {
        List<CodonAminoAcidUI> uiList = new ArrayList<>();

        for (String codon : result.getCodonExplanations().keySet()) {
            CodonMapping mapping = codonRepo.findByCodon(codon);
            if (mapping == null)
                continue;

            uiList.add(
                    new CodonAminoAcidUI(
                            codon,
                            mapping.getAminoAcid(), // ⭐ KEY PART
                            result.getCodonExplanations().get(codon),
                            result.getCodonCounts().getOrDefault(codon, 0)));
        }
        return uiList;
    }

}
