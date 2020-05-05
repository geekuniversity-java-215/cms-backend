package com.github.geekuniversity_java_215.cmsbackend.chat.basic;

import com.github.geekuniversity_java_215.cmsbackend.chat.ChatApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ChatApplication.class})
@Slf4j
public class MoarTests {


    @Test
    void moarTst() throws Exception {
        log.info("Moar test!");
    }

}
