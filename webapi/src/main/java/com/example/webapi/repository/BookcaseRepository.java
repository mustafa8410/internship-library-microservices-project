package com.example.webapi.repository;

import com.example.webapi.entity.Bookcase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookcaseRepository extends JpaRepository<Bookcase, Long> {
    boolean existsBookcaseById(Long id);
    List<Bookcase> findAllByIdIsNotOrderByCapacityDesc(Long id);
    List<Bookcase> findAllByOrderByCapacityDesc();
}
