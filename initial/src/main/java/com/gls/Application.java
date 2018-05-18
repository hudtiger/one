package com.gls;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.gls.demo.repository.InventoryRepository;
import com.gls.demo.repository.MongoExample;

@SpringBootApplication
//@EnableCaching //启用缓存
public class Application implements CommandLineRunner{
	
	@Autowired
	private MongoExample mongo;
	
	@Autowired
	private InventoryRepository repository;

	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
	}

	public void run(String... args){
		//repository.findByStatus("A").forEach(inventory->{System.out.println(JSON.toJSONString(inventory));});
		mongo.simpleQuery();
		mongo.mapreduce();
		mongo.aggregate();
	}
}

