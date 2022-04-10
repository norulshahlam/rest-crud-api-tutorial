package com.shah.restcrudapitutorial.startup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyStartup implements CommandLineRunner{
	
	@Value("${app.name}")
	private String appName;

	@Override
	public void run(String... args) throws Exception {
		log.info("Application name: "+appName+" just loaded!");
		
	}
}
