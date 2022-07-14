package com.gsitm.ustra.tutorial.batch.task;

import com.gsitm.ustra.java.batch.task.UstraBatchSimpleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component("SIMPLE_TASK")
public class SimpleTask extends UstraBatchSimpleTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTask.class);

    @Override
    protected void execute() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final String datetimeString = LocalDateTime.now().format(formatter);
        LOGGER.info("{} | SIMPLE TASK runs", datetimeString);
    }
}
