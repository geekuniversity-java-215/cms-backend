package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.services;

import com.github.geekuniversity_java_215.cmsbackend.cmsapplication.data.CalculatorResult;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public CalculatorResult add(double a, double b) {

        return new CalculatorResult(a + b);
    }

    public CalculatorResult sub(double a, double b) {
        return new CalculatorResult(a - b);
    }

    public CalculatorResult mul(double a, double b) {
        return new CalculatorResult(a * b);
    }

    public CalculatorResult div(double a, double b) {
        return new CalculatorResult(a / b);
    }
}
