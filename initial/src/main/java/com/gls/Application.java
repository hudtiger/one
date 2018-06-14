package com.gls;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.gls.demo.repository.InventoryRepository;
import com.gls.demo.repository.MongoExample;
import com.gls.storage.StorageProperties;
import com.gls.storage.StorageService;

@SpringBootApplication
@EnableCaching //启用缓存
//@EnableScheduling//开启定时任务
@EnableConfigurationProperties(StorageProperties.class)
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
	
	 @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}

