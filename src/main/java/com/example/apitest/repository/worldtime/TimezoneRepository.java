package com.example.apitest.repository.worldtime;

import com.example.apitest.model.worldtime.Timezone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimezoneRepository extends JpaRepository<Timezone, Long> {
}
