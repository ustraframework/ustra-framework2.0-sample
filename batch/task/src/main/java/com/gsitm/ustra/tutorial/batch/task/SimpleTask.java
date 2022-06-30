package com.gsitm.ustra.tutorial.batch.task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    @Autowired
    @Qualifier("src")
    private DataSource srcDataSource;

    @Autowired
    @Qualifier("dest")
    private DataSource destDataSource;

    @Override
    protected void execute() {
        final List<Integer> data = new ArrayList<>();
        loadData(data);
        storeData(data);
    }

    private List<Integer> loadData(List<Integer> data) {
        try {
            final Connection conn = srcDataSource.getConnection();
            final Statement stmt = conn.createStatement();
            final ResultSet rs = stmt.executeQuery("SELECT TS FROM SIMPLE_DATA");
            while(rs.next()) {
                final int ts = rs.getInt(1);
                data.add(ts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private void storeData(List<Integer> data) {
        try {
            final Connection conn = destDataSource.getConnection();
            final Statement stmt = conn.createStatement();
            data.forEach(each -> {
                try {
                    stmt.executeUpdate("INSERT INTO SIMPLE_DATA (TS) VALUES (" + each + ")");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
