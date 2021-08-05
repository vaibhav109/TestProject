package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.entities.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Integer>
{
}
