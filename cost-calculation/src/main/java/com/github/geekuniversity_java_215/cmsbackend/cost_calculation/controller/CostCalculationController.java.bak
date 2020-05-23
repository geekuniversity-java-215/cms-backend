package com.github.geekuniversity_java_215.cmsbackend.cost_calculation.controller;

import com.github.geekuniversity_java_215.cmsbackend.cost_calculation.entity.*;
import com.github.geekuniversity_java_215.cmsbackend.cost_calculation.service.CostCalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class CostCalculationController {
    private CostCalculationService costCalculationService;

    @Autowired
    public CostCalculationController(CostCalculationService costCalculationService) {
        this.costCalculationService = costCalculationService;
    }

    @PostMapping("/calculation")
    public TempCost calculateCostShipping(TempOrder tempOrder) {
        return costCalculationService.calculateCostShipping(tempOrder.getDistance(), tempOrder.getWeight(), tempOrder.isUrgency());
    }
}
