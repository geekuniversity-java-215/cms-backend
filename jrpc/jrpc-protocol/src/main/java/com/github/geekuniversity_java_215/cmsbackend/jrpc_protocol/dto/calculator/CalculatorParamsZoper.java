package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.calculator;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CalculatorParamsZoper {
    private double l0;
    private List<Double> list = new ArrayList<>();
}
