package com.rabigol.wowmoneywebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.rabigol.wowmoneywebapp.utils.DateGetter.dateGetter;

/**
 * Created by Artur.Ziaev on 05.11.2016.
 */

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
//    private PostRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    }
}
