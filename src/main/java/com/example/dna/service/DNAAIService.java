package com.example.dna.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DNAAIService {

    private final ChatClient chatClient;

    @Autowired
    public DNAAIService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String analyzeWithAI(String dna, String mrna, String protein) {

        String prompt = """
                You are an expert in molecular biology and genetics.

                Analyze the following sequences:

                DNA sequence:
                %s

                mRNA sequence:
                %s

                Protein (amino acid) sequence:
                %s

                Explain clearly for a student:
                1. What is the function of the predicted protein.
                2. Can this DNA mutation be linked to a known disease? If so, which one and what are the symptoms.
                3. What biological processes is this gene involved in.
                4. What type of mutation is present (SNP, insertion, deletion) and how might it affect the protein function.
                5. Can this sequence be used as a biomarker for any condition? If so, which one and how is it detected.
                6. What experiments would you suggest for validating this sequence’s function and its role in disease.

                Use simple, structured paragraphs.
                """
                .formatted(dna, mrna, protein);

        try {
            return chatClient
                    .prompt(prompt)
                    .call()
                    .content();
        } catch (Exception e) {
            // ✅ Don’t crash the app – just return a friendly fallback
            return "AI explanation is temporarily unavailable. "
                    + "Please try again later. (Error: " + e.getClass().getSimpleName() + ")";
        }
    }
}
