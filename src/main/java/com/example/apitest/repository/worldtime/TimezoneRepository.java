package com.example.apitest.repository.worldtime;

import com.example.apitest.model.worldtime.Timezone;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * O repositorio para Timezone
 */
public interface TimezoneRepository extends JpaRepository<Timezone, Long> {
}
