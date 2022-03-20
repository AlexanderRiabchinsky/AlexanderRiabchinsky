package main;

import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.apache.logging.log4j.LogManager.getLogger;


@SpringBootApplication
public class Main implements CommandLineRunner {
    private static final Logger log = getLogger(Main.class);
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
    @Override
    public void run(String... args) throws Exception {
        log.info("Started");
        DBConnection.getConnection();
    }
}
