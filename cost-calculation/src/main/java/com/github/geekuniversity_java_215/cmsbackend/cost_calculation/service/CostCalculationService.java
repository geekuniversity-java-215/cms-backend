package com.github.geekuniversity_java_215.cmsbackend.cost_calculation.service;


import com.github.geekuniversity_java_215.cmsbackend.cost_calculation.data.TempCost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CostCalculationService {

    private static final double COST_KILOMETER = 35.0;
    private static final double COEFFICIENT_URGENCY = 1.5;


    public TempCost calculateCostShipping(int distance, double weight, boolean urgency) {
        double coefficient_weight = getCoefficientWeight(weight);
        double price;

        if (urgency) {
            price = distance * COST_KILOMETER * coefficient_weight * COEFFICIENT_URGENCY;
        } else {
            price = distance * COST_KILOMETER * coefficient_weight;
        }
        TempCost tempCost = new TempCost();
        tempCost.setCost(price);

        log.info("cost shipping calculated successfully");
        return tempCost;
    }

    private double getCoefficientWeight(double weight) {
        if (weight > 0 && weight <= 10) return 1.0;
        else if (weight > 10 && weight <= 30) return 1.2;
        else return 1.4;
    }
}
