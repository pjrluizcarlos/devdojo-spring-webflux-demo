package br.com.luizcarlospjr.devdojospringwebfluxdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

import java.util.zip.InflaterInputStream;

@SpringBootApplication
public class DevdojoSpringWebfluxDemoApplication {

	static {
		BlockHound.install(b -> b.allowBlockingCallsInside(InflaterInputStream.class.getName(), "read"));
	}

	public static void main(String[] args) {
		SpringApplication.run(DevdojoSpringWebfluxDemoApplication.class, args);
	}

}
