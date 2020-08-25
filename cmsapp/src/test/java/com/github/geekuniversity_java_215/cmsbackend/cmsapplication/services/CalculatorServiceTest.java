package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.services;

import com.github.geekuniversity_java_215.cmsbackend.utils.Junit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith({Junit5Extension.class})
class CalculatorServiceTest {

    @Test
    void add() {
        System.out.println("213");
    }
}