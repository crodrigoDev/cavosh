package com.api.cavosh;

import org.springframework.boot.SpringApplication;

public class TestCavoshApplication {

    public static void main(String[] args) {
        SpringApplication.from(CavoshApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
