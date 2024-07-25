package com.developer.soumya.repository;

import com.developer.soumya.model.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZipCodeRepository extends JpaRepository<ZipCode, Long> {
}