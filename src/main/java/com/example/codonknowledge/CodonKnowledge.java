package com.example.codonknowledge;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CodonKnowledge {

    private static final Map<String, String> CODON_EXPLANATIONS = createMap();

    private static Map<String, String> createMap() {
        Map<String, String> map = new LinkedHashMap<>();

        // --- U row ---

        // Phenylalanine (Phe)
        map.put("UUU",
                "UUU – Hydrophobic aromatic amino acid. \r\n" + //
                        "Commonly packed inside protein cores and essential for stabilizing folded structures through hydrophobic interactions");
        map.put("UUC",
                "UUC – Phenylalanine (Phe): Aromatic ring allows stacking interactions. \r\n" + //
                        "Frequently appears in enzymes where it helps maintain structural rigidity.");

        // Leucine (Leu)
        map.put("UUA",
                "UUA – Strongly hydrophobic amino acid. \r\n" + //
                        "Important in forming α-helices and buried core regions in proteins.");
        map.put("UUG",
                "UUG – Leucine (Leu): Helps stabilize membrane proteins and hydrophobic domains. \r\n" + //
                        "Frequently appears in structural motifs.");

        // Serine (Ser)
        map.put("UCU",
                "UCU – Serine (Ser): Polar amino acid with an –OH group. \r\n" + //
                        "Major site for phosphorylation during cell signaling pathways.");
        map.put("UCC",
                "UCC – Serine (Ser): Appears in enzyme active sites and helps stabilize catalytic intermediates through hydrogen bonding.");
        map.put("UCA",
                "UCA – Serine (Ser): Often modified during post-translational regulation (phosphorylation/glycosylation).");
        map.put("UCG",
                "UCG – Serine (Ser): Helps maintain protein solubility and is commonly found on protein surfaces.");

        // Tyrosine (Tyr)
        map.put("UAU",
                "UAU – Tyrosine (Tyr): Aromatic amino acid often phosphorylated in signal transduction (e.g., receptor kinases). \r\n"
                        + //
                        "Important for enzyme regulation.");
        map.put("UAC", "UAC – Tyrosine (Tyr): Absorbs UV light (280nm).\r\n" + //
                "Frequently used in active sites and protein-protein interaction motifs.");
        // STOP codons
        map.put("UAA",
                "UAA – STOP codon: signals the ribosome to stop translation and release the completed polypeptide chain.");
        map.put("UAG", "UAG – STOP codon: termination signal; no amino acid is added, translation ends here.");
        map.put("UGA",
                "UGA – STOP codon: standard stop signal; in some organisms it can code for selenocysteine, but usually ends translation.");

        // Cysteine (Cys)
        map.put("UGU",
                "UGU – Cysteine (Cys): contains a sulfur atom; forms disulfide bonds that stabilize protein 3D structure.");
        map.put("UGC",
                "UGC – Cysteine (Cys): Key residue in redox reactions and metal binding. \r\n" + //
                        "Important in catalytic triads and structural motifs.");

        // Tryptophan (Trp)
        map.put("UGG",
                "UGG – Tryptophan (Trp): Critical for protein folding, fluorescence, and membrane interactions. \r\n" + //
                        "Precursor for serotonin.");

        // --- C row ---

        // Leucine (Leu)
        map.put("CUU", "CUU – Leucine (Leu): hydrophobic amino acid that helps pack protein interiors tightly.");
        map.put("CUC", "CUC – Leucine (Leu): frequently used in structural and membrane proteins.");
        map.put("CUA", "CUA – Leucine (Leu): contributes to stable helices and hydrophobic cores.");
        map.put("CUG",
                "CUG – Leucine (Leu): one of the most common codons in many organisms, used in stable structural proteins.");

        // Proline (Pro)
        map.put("CCU", "CCU – Proline (Pro): unique ring structure introduces bends/kinks in polypeptide chains.");
        map.put("CCC", "CCC – Proline (Pro): often found in turns and loops; can rigidify protein backbones.");
        map.put("CCA", "CCA – Proline (Pro): important in collagen and connective-tissue proteins.");
        map.put("CCG", "CCG – Proline (Pro): helps define sharp turns in protein structures.");

        // Histidine (His)
        map.put("CAU",
                "CAU – Histidine (His): side chain can gain or lose a proton near physiological pH; common in enzyme active sites.");
        map.put("CAC", "CAC – Histidine (His): important for catalysis and metal-ion binding in many enzymes.");

        // Glutamine (Gln)
        map.put("CAA",
                "CAA – Glutamine (Gln): polar amino acid; used in nitrogen transport and protein stabilization.");
        map.put("CAG",
                "CAG – Glutamine (Gln): contributes to hydrogen bonding; repeated CAGs are linked to certain genetic disorders.");

        // Arginine (Arg)
        map.put("CGU",
                "CGU – Arginine (Arg): positively charged; interacts with DNA/RNA and often found in binding sites.");
        map.put("CGC",
                "CGC – Arginine (Arg): strong positive charge helps stabilize negative phosphate groups in nucleic acids.");
        map.put("CGA", "CGA – Arginine (Arg): involved in active sites of enzymes and in cell signaling.");
        map.put("CGG", "CGG – Arginine (Arg): appears in DNA/RNA binding proteins; rich in basic residues.");

        // --- A row ---

        // Isoleucine (Ile)
        map.put("AUU", "AUU – Isoleucine (Ile): hydrophobic; helps maintain the core of globular proteins.");
        map.put("AUC",
                "AUC – Isoleucine (Ile): frequently used in membrane-spanning segments and stable protein regions.");
        map.put("AUA", "AUA – Isoleucine (Ile): contributes to energy-related and structural proteins.");

        // Methionine (Met) – START
        map.put("AUG",
                "AUG – Methionine (Met): START codon in most organisms. Marks where translation begins and also provides the first amino acid.");

        // Threonine (Thr)
        map.put("ACU",
                "ACU – Threonine (Thr): polar; its –OH group can be phosphorylated, regulating enzyme activity.");
        map.put("ACC", "ACC – Threonine (Thr): participates in hydrogen bonding and surface interactions of proteins.");
        map.put("ACA", "ACA – Threonine (Thr): often found in active sites and regulatory regions.");
        map.put("ACG", "ACG – Threonine (Thr): contributes to protein flexibility and signaling functions.");

        // Asparagine (Asn)
        map.put("AAU",
                "AAU – Asparagine (Asn): polar; frequently used at protein surfaces and in N-linked glycosylation sites.");
        map.put("AAC", "AAC – Asparagine (Asn): helps in forming hydrogen bonds and maintaining protein solubility.");

        // Lysine (Lys)
        map.put("AAA",
                "AAA – Lysine (Lys): positively charged; important for DNA binding, histone modification and protein–protein interactions.");
        map.put("AAG", "AAG – Lysine (Lys): often acetylated or methylated in histones, influencing gene expression.");

        // Serine (Ser)
        map.put("AGU", "AGU – Serine (Ser): polar; participates in catalysis and is a common phosphorylation site.");
        map.put("AGC", "AGC – Serine (Ser): involved in signal transduction and enzyme regulation.");

        // Arginine (Arg)
        map.put("AGA",
                "AGA – Arginine (Arg): basic amino acid; often appears in nuclear localization signals and DNA-binding motifs.");
        map.put("AGG",
                "AGG – Arginine (Arg): contributes to strong electrostatic interactions with negatively charged molecules.");

        // --- G row ---

        // Valine (Val)
        map.put("GUU", "GUU – Valine (Val): branched hydrophobic amino acid; helps build stable cores of proteins.");
        map.put("GUC", "GUC – Valine (Val): frequently found in enzymes and structural proteins.");
        map.put("GUA", "GUA – Valine (Val): supports hydrophobic packing and protein stability.");
        map.put("GUG",
                "GUG – Valine (Val): sometimes used as an alternative start codon in bacteria, but usually codes for valine.");

        // Alanine (Ala)
        map.put("GCU", "GCU – Alanine (Ala): small, non-polar; often used in flexible regions and mutational studies.");
        map.put("GCC", "GCC – Alanine (Ala): common in alpha-helices and simple structural motifs.");
        map.put("GCA",
                "GCA – Alanine (Ala): helps maintain overall protein structure without adding strong charge or polarity.");
        map.put("GCG",
                "GCG – Alanine (Ala): frequently used codon in many organisms, especially in structural proteins.");

        // Aspartic acid (Asp)
        map.put("GAU",
                "GAU – Aspartic acid (Asp): negatively charged; key in enzyme active sites and acid–base catalysis.");
        map.put("GAC", "GAC – Aspartic acid (Asp): often coordinates metal ions and participates in catalytic triads.");

        // Glutamic acid (Glu)
        map.put("GAA",
                "GAA – Glutamic acid (Glu): acidic amino acid; takes part in signaling, metabolism, and catalysis.");
        map.put("GAG",
                "GAG – Glutamic acid (Glu): common in active sites and important for protein solubility and charge balance.");

        // Glycine (Gly)
        map.put("GGU",
                "GGU – Glycine (Gly): smallest amino acid; allows tight turns and high flexibility in proteins.");
        map.put("GGC", "GGC – Glycine (Gly): found in loop regions and in the ‘glycine-rich’ motifs of many enzymes.");
        map.put("GGA", "GGA – Glycine (Gly): supports sharp bends and hinge regions in polypeptide chains.");
        map.put("GGG", "GGG – Glycine (Gly): often used in flexible linkers and protein engineering designs.");

        return Collections.unmodifiableMap(map);
    }

    /**
     * Get an educational explanation for a single codon.
     */
    public static String getExplanation(String codon) {
        if (codon == null || codon.isBlank()) {
            return "No codon provided. A codon is a sequence of three RNA bases (A, U, G, C).";
        }
        String key = codon.trim().toUpperCase();
        String explanation = CODON_EXPLANATIONS.get(key);

        if (explanation != null) {
            return explanation;
        }

        if (key.length() == 3) {
            return key
                    + " – This is not a standard mRNA codon in the universal genetic code, or it may be invalid. A codon should use A, U, G, or C.";
        } else {
            return "Input '" + codon + "' is not a valid codon. A codon must be exactly three RNA bases (A, U, G, C).";
        }
    }

    /**
     * Get the full codon → explanation map.
     * Useful if you want to display a learning table for students.
     */
    public static Map<String, String> getAllCodonExplanations() {
        return CODON_EXPLANATIONS;
    }
}
