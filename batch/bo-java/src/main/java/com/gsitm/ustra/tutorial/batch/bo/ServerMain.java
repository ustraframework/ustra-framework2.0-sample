package com.gsitm.ustra.tutorial.batch.bo;

import com.gsitm.ustra.java.mvc.app.ServletApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
public class ServerMain extends ServletApplicationRunner{
	public static void main(String[] args) throws IOException {
		ServletApplicationRunner.run(ServerMain.class, args);
	}

}
