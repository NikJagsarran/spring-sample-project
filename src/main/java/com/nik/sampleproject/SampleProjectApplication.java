package com.nik.sampleproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

//import com.nik.sampleproject.hobbies.HobbyRepository;

@SpringBootApplication
public class SampleProjectApplication implements CommandLineRunner {
	//@Autowired
	//private HobbyRepository hobbyRepo;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(SampleProjectApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		//initHobbyDB();
		//initKafkaTests();
	}
	
	private void initHobbyDB() {
		//String pathName = "..\\..\\..\\..\\..\\..\\data\\";
		String fileName = "hobbiesInit.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		/*
		hobbyRepo.deleteAll();

		// save a couple of customers
		hobbyRepo.save(new Customer("Alice", "Smith"));
		hobbyRepo.save(new Customer("Bob", "Smith"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : hobbyRepo.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(hobbyRepo.findByFirstName("Alice"));

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Customer customer : hobbyRepo.findByLastName("Smith")) {
			System.out.println(customer);
		}*/
	}
	
	private void initKafkaTests() {
		String topicName = "InitalSpringTest";
		kafkaTemplate.send(topicName, "Sending message " + new SimpleDateFormat("yyyy-MM-dd HH:mmss").format(new Date()));
	}
	
	@KafkaListener(topics = "InitalSpringTest", groupId = "group-id")
	public void listen(String msg) {
	   System.out.println("Received Message in group - group-id: " + msg);
	}
}