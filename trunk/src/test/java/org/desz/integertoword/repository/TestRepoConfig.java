package org.desz.integertoword.repository;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration()
@PropertySource(value = { "classpath:test.mongo.properties" })
public class TestRepoConfig {
	@Inject
	private Environment env;

	public @Bean MongoDbFactory dbFactory() {
		MongoDbFactory db;
		try {
			db = new SimpleMongoDbFactory(new MongoClient(env.getProperty("mongo.db")), "test_number_freq");
		} catch (Exception e) {

			throw new RuntimeException(e.getMessage());
		}
		return db;
	}
}
