package com.example.apitest.dto.worldtime;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimezoneDto {

    private String utc_offset;
    private String timezone;
    private int day_of_week;
    private int day_of_year;
    private OffsetDateTime datetime;
    private OffsetDateTime utc_datetime;
    private long unixtime;
    private int week_number;
}
