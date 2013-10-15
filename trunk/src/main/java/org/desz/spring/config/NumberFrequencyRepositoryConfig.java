package org.desz.spring.config;

import javax.inject.Inject;

import org.desz.numbertoword.repository.NumberFrequencyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoURI;

/**
 * Configuration for Mongo document database
 * 
 * @author des
 * 
 */
@Configuration()
@PropertySource(value = { "classpath:mongo.properties" })
public class NumberFrequencyRepositoryConfig {

	@Inject
	private Environment environment;

	public @Bean
	MongoDbFactory mongoDbFactory() {
		MongoDbFactory db;
		try {
			db = new SimpleMongoDbFactory(new MongoURI(
					environment.getProperty("mongo.db")));
		} catch (Exception e) {

			throw new RuntimeException(e.getMessage());
		}
		return db;
	}

	public @Bean()
	MongoOperations mongoTemplate() {

		MongoOperations mongoTemplate;
		try {
			mongoTemplate = new MongoTemplate(mongoDbFactory());

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return mongoTemplate;

	}

	public @Bean()
	NumberFrequencyRepository numberFrequencyRepository() {
		NumberFrequencyRepository numberFrequencyRepository = null;
		try {
			numberFrequencyRepository = new NumberFrequencyRepository(
					mongoTemplate());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		return numberFrequencyRepository;
	}

}
