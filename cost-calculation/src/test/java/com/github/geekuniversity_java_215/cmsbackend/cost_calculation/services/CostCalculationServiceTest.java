package com.github.geekuniversity_java_215.cmsbackend.cost_calculation.services;

import com.github.geekuniversity_java_215.cmsbackend.cost_calculation.service.CostCalculationService;
import com.github.geekuniversity_java_215.cmsbackend.utils.Junit5Extension;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
@ExtendWith({Junit5Extension.class})
@Slf4j
public class CostCalculationServiceTest {
    private static int distance;
    private static double weight;

    @Autowired
    private CostCalculationService costCalculationService;

    @BeforeAll
    public static void setUp(){
        distance = 10;
        weight = 9.0;
    }

    @Test
    public void calculateCostShipping(){
        List<Double> listCost = costCalculationService.calculateCostShipping(distance,weight);
        log.info("cost shipping calculated successfully");
    }

}
