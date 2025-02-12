package com.example.batch.repository;

import com.example.batch.entity.Bookcase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookcaseRepository extends JpaRepository<Bookcase, Long> {
}
