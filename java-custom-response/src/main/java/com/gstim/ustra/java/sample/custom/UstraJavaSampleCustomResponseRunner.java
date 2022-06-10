package com.gstim.ustra.java.sample.custom;

import com.gsitm.ustra.java.mvc.app.ServletApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class UstraJavaSampleCustomResponseRunner extends ServletApplicationRunner
{
    public static void main(String[] args) throws IOException
    {
        ServletApplicationRunner.run(UstraJavaSampleCustomResponseRunner.class, args);
    }
}
