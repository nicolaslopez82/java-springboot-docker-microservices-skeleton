package com.nicolaslopez82.sms.repository;

import com.nicolaslopez82.sms.domain.Tour;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TourRepository extends CrudRepository<Tour, Integer> {

    Optional<Tour> findByTitle(String title);
}
