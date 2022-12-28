package com.example.exercisesix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ExerciseSixApplication.class))
class ExerciseSixApplicationTests {

    public static void main(String[] args) {
        SpringApplication.run(ExerciseSixApplication.class, args);
    }

}
