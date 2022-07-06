package com.gsitm.ustra.tutorial.batch.task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gsitm.ustra.java.batch.task.UstraBatchSimpleTask;

@Component("SIMPLE_TASK")
public class SimpleTask extends UstraBatchSimpleTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTask.class);

    @Override
    protected void execute() {
        LOGGER.info("{} | SIMPLE TASK runs", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
