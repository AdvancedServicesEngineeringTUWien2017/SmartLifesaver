package com.filip.smartlifesaver;

import com.filip.smartlifesaver.ml.IClassifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartLifeSaverApplication implements CommandLineRunner {



	@Autowired
	private IClassifier classifier;

	public static void main(String[] args) {
		SpringApplication.run(SmartLifeSaverApplication.class, args);
	}


	@Override
	public void run(String... strings) throws Exception {

		//classifier.trainAndSaveBestModel();

		classifier.loadBestTrainedModel();

	}
}
