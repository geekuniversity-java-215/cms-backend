package com.github.geekuniversity_java_215.cmsbackend.cost_calculation.services;

import com.github.geekuniversity_java_215.cmsbackend.cost_calculation.entity.TempCost;
import com.github.geekuniversity_java_215.cmsbackend.cost_calculation.service.CostCalculationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class CostCalculationServiceTest {
    private static int distance;
    private static double weight;
    private static boolean urgency;

    @Autowired
    private CostCalculationService costCalculationService;

    @BeforeAll
    public static void setUp(){
        distance = 10;
        weight = 9.0;
        urgency = true;
    }

    @Test
    public void calculateCostShipping(){
        TempCost tempCostActualy = costCalculationService.calculateCostShipping(distance,weight,urgency);
        log.info("cost shipping calculated successfully");
    }

}
