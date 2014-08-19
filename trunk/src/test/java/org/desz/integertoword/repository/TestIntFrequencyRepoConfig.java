package org.desz.integertoword.repository;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoURI;

@Configuration()
@PropertySource(value = { "classpath:test.mongo.properties" })
public class TestIntFrequencyRepoConfig {
	@Inject
	private Environment env;

	public @Bean MongoDbFactory dbFactory() {
		MongoDbFactory db;
		try {
			db = new SimpleMongoDbFactory(new MongoURI(
					env.getProperty("mongo.db")));
		} catch (Exception e) {

			throw new RuntimeException(e.getMessage());
		}
		return db;
	}
}
