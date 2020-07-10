package com.nicolaslopez82.sms.repository;

import com.nicolaslopez82.sms.domain.Tour;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<Tour, Integer> {
}
