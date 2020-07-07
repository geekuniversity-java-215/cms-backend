package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.services;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public Double add(double a, double b) {
        return a + b;
    }

    public Double sub(double a, double b) {
        return a - b;
    }

    public Double mul(double a, double b) {
        return a * b;
    }

    public Double div(double a, double b) {
        return a / b;
    }
}
