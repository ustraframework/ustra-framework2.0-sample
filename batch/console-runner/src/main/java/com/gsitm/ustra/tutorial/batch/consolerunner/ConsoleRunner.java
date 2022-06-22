package com.gsitm.ustra.tutorial.batch.consolerunner;

import com.gsitm.ustra.java.batch.runner.CommandLineTaskRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsoleRunner extends CommandLineTaskRunner {
    public ConsoleRunner() {
        super(ConsoleRunner.class);
    }

    public static void main(String ...args) {
        ConsoleRunner.run(ConsoleRunner.class, args);
    }
}
