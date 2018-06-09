package im.hdy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class StartClass {
    public static void main(String[] args) {
        SpringApplication.run(StartClass.class, args);
    }
}
