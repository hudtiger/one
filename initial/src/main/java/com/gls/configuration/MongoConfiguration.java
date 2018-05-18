package com.gls.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

//@Configuration
public class MongoConfiguration {

//	@Bean(name="mongoTemplate")
//	@Qualifier
//	public MongoTemplate init() {
//		
//		MongoClientURI uri = new MongoClientURI("mongodb://192.168.56.102:27017/test");
//		SimpleMongoDbFactory dbFactory = new SimpleMongoDbFactory(uri);
//		return new MongoTemplate(dbFactory);
//	}
//	
//	@Bean(name="mongoTemplate")
//	@Qualifier
//	public MongoTemplate init() {	
//		MongoClient client = new MongoClient("192.168.56.102",27017);
//		return new MongoTemplate(client,"test");
//	}

//	@Bean
//    public MongoOperations mongoOperations(MongoTemplate mongoTemplate) {
//        return mongoTemplate;
//    }
}