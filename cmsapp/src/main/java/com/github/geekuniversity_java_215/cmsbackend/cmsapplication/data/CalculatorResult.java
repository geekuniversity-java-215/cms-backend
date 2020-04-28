package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

// Такие штуки должны уехать в DTO в protocol
@Data
public class CalculatorResult {
    private double result;
    private Instant timestamp = Instant.now();

    public CalculatorResult(double result) {
        this.result = result;
    }

    private void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
