package com.example.demofiveminutelog;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class Controller {
    @GetMapping("/hello")
    public void hello() {
        System.out.println("Hello World");
        Logger tloLogger = LoggerFactory.getLogger("tloLogger");
        tloLogger.info("Hello World");
    }
}
