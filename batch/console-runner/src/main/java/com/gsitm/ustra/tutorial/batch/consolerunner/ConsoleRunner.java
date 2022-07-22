package com.gsitm.ustra.tutorial.batch.consolerunner;

import com.gsitm.ustra.java.batch.runner.CommandLineTaskRunner;
import com.gsitm.ustra.java.core.app.ConsoleApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsoleRunner extends CommandLineTaskRunner {
    public static void main(String ...args) throws Exception {
        ConsoleApplicationRunner.run(ConsoleRunner.class, args);
    }
}
