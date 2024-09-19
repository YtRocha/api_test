package com.example.apitest.model.worldtime;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;

/**
 * A entidade Timezone
 */
@Entity
@Data
public class Timezone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String utc_offset;
    @Column(nullable = false)
    private String timezone;
    @Column(nullable = false)
    private int day_of_week;
    @Column(nullable = false)
    private int day_of_year;
    @Column(nullable = false)
    private OffsetDateTime datetime;
    @Column(nullable = false)
    private OffsetDateTime utc_datetime;
    @Column(nullable = false)
    private long unixtime;
    @Column(nullable = false)
    private int week_number;
}