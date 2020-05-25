package com.github.geekuniversity_java_215.cmsbackend.cost_calculation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CostCalculationService {

    private static final double COST_KILOMETER = 35.0;
    private static final double COEFFICIENT_FRAGILITY = 1.3;
    private static final double COEFFICIENT_URGENCY = 1.5;


    public List<Double> calculateCostShipping(int distance, double weight) {
        double coefficient_weight = getCoefficientWeight(weight);

        List<Double> listCost = new ArrayList<>();
        double basePrice = distance * COST_KILOMETER * coefficient_weight;
        double addPriceFragility = basePrice * COEFFICIENT_FRAGILITY - basePrice;
        double addPriceUrgency = basePrice * COEFFICIENT_URGENCY - basePrice;

        listCost.add(basePrice);
        listCost.add(addPriceFragility);
        listCost.add(addPriceUrgency);

        log.info("cost shipping calculated successfully");
        return listCost;
    }

    private double getCoefficientWeight(double weight) {
        if (weight > 0 && weight <= 10) return 1.0;
        else if (weight > 10 && weight <= 30) return 1.2;
        else return 1.4;
    }
}
