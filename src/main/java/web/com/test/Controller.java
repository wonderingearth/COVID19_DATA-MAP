package web.com.test;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Controller {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Controller.class, args);
        //ConfigurableApplicationContext ctx = SpringApplication.run(Controller.class, args);
        //TimeUnit.SECONDS.sleep(5);
        //ctx.close();
    }
}