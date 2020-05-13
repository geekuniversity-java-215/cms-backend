package com.github.geekuniversity_java_215.cmsbackend.cost_calculation.entity;

import lombok.Data;
import org.springframework.stereotype.Component;


@Component
@Data
public class TempOrder {

    private int distance;
    private double weight;
    private boolean urgency;
}
