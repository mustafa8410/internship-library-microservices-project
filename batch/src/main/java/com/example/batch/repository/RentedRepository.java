package com.example.batch.repository;

import com.example.batch.entity.Rented;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RentedRepository extends JpaRepository<Rented, Long> {
    List<Rented> findAllByEndDateBeforeAndAlertSentIsFalse(Date date);
    boolean existsByBookId(Long bookId);
}
