package com.gsitm.ustra.tutorial.batch.task;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.gsitm.ustra.java.test.UstraSpringTest;
import com.gsitm.ustra.tutorial.batch.task.config.BatchTutorialConfig;

@EnableAutoConfiguration
@SpringBootTest(classes = BatchTutorialConfig.class)
@ComponentScan("com.gsitm.ustra.tutorial")
public class SimpleTaskTest extends UstraSpringTest {
    @Autowired
    private SimpleTask simpleTask;

    @Test
    public void testExecute() throws SQLException {
        simpleTask.execute();
    }

    @AfterEach
    public void afterEach() {

    }
}
