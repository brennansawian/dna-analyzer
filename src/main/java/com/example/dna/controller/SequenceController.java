package com.example.dna.controller;

import com.example.dna.model.Sequence;
import com.example.dna.service.DNAAIService;
import com.example.dna.service.SequenceService;
import com.example.dna.service.SequenceService.mRNATranslator.TranslationResult;
import com.example.dna.service.SequenceService.CodonAminoAcidUI;
import com.example.dna.service.SequenceService.KnowledgeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class SequenceController {

    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private DNAAIService aiService;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("sequence", new Sequence());
        return "index";
    }

    @PostMapping("/analyze")
    public String analyzeSequence(@ModelAttribute Sequence sequence, Model model) {

        String dna = sequence.getSequence().toUpperCase().trim();
        String mrna = sequenceService.transcribeToMRNA(dna);
        KnowledgeResult kr = sequenceService.translateToProteinWithKnowledge(mrna);
        List<CodonAminoAcidUI> codonUI = sequenceService.buildCodonAminoAcidUI(kr);

        SequenceService.mRNATranslator translator = sequenceService.new mRNATranslator();
        TranslationResult result = translator.translateToProtein(mrna);

        KnowledgeResult knowledgeResult = sequenceService.translateToProteinWithKnowledge(mrna);

        SequenceService.CodingRegionResult region = sequenceService.extractCodingRegion(mrna);

        String aiExplanation = aiService.analyzeWithAI(
                dna,
                mrna,
                result.getProteinSequence());
        model.addAttribute("dnaSequence", dna);
        model.addAttribute("mrnaSequence", mrna);

        model.addAttribute("aminoAcidSequence", knowledgeResult.getProteinSequence());

        model.addAttribute("codonExplanations", knowledgeResult.getCodonExplanations());
        model.addAttribute("codonCounts", knowledgeResult.getCodonCounts());
        model.addAttribute("maxCodonCount", knowledgeResult.getMaxCodonCount());

        model.addAttribute("codingBases", region.getCodingCount());
        model.addAttribute("nonCodingBases", region.getNonCodingCount());
        model.addAttribute("codingPercentage", region.getCodingPercentage());
        model.addAttribute("nonCodingPercentage", region.getNonCodingPercentage());

        model.addAttribute("codingSequence", region.getCodingSequence());
        model.addAttribute("nonCodingSequence", region.getNonCodingSequence());
        model.addAttribute("codingCount", region.getCodingCount());
        model.addAttribute("nonCodingCount", region.getNonCodingCount());
        model.addAttribute("codingPercent", region.getCodingPercentage());
        model.addAttribute("nonCodingPercent", region.getNonCodingPercentage());
        model.addAttribute("codonUI", codonUI);

        // Add to model so Thymeleaf can use it
        model.addAttribute("aiExplanation", aiExplanation);

        return "result";
    }
}
