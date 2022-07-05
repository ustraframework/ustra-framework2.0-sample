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

    @Autowired
    @Qualifier("src")
    private DataSource srcDataSource;

    @Autowired
    @Qualifier("dest")
    private DataSource destDataSource;

    private List<Integer> data = new ArrayList<>();

    @BeforeEach
    public void beforeEach() throws SQLException {
        initData();
        createSourceDatabase();
        createTargetDatabase();
    }

    @Test
    public void testExecute() throws SQLException {
        simpleTask.execute();
        ResultSet rs = destDataSource.getConnection().createStatement().executeQuery("SELECT COUNT(1) FROM SIMPLE_DATA");
        long count = rs.next() ? rs.getLong(1) : 0;
        assertEquals(10, count);
    }

    private void initData() {
        final Random random = new Random();
        random.ints(10).forEach(data::add);
    }

    private void createTargetDatabase() throws SQLException {
        final Connection conn = destDataSource.getConnection();
        conn.setAutoCommit(true);

        final Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE SIMPLE_DATA (TS INTEGER NOT NULL)");
    }

    private void createSourceDatabase() throws SQLException {
        final Connection conn = srcDataSource.getConnection();
        conn.setAutoCommit(true);

        // create table
        final Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE SIMPLE_DATA (TS INTEGER NOT NULL)");

        // insert data
        final PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SIMPLE_DATA (TS) VALUES (?)");
        data.stream().forEach(each -> {
            try {
                pstmt.setInt(1, each);
                pstmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @AfterEach
    public void afterEach() {

    }
}