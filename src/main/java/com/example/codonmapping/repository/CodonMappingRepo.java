package com.example.codonmapping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.codonmapping.model.CodonMapping;

@Repository
public interface CodonMappingRepo extends JpaRepository<CodonMapping, Long> {
    CodonMapping findByCodon(String codon);
}
