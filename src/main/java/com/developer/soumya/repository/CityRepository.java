package com.developer.soumya.repository;

import com.developer.soumya.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}