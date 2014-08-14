package org.desz.integertoword.spring.config;

import javax.inject.Inject;

import org.desz.integertoword.exceptions.MongoDbException;
import org.desz.integertoword.repository.INumberFreqRepo;
import org.desz.integertoword.repository.NumberFrequencyRepository;
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
 * Configuration for Mongo test database
 * 
 * @author des
 * 
 */
@Configuration()
@PropertySource(value = { "classpath:test.mongo.properties" })
public class NumberFrequencyRepositoryConfigTest {

	@Inject
	private Environment environment;

	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		MongoDbFactory db;
		try {
			db = new SimpleMongoDbFactory(new MongoURI(
					environment.getProperty("test.db")));

		} catch (Exception e) {
			throw new MongoDbException(e.getMessage());
		}
		return db;
	}

	public @Bean() MongoOperations mongoTemplate() throws Exception {

		MongoTemplate mongoTemplate;
		try {
			mongoTemplate = new MongoTemplate(mongoDbFactory());

		} catch (Exception e) {
			throw new MongoDbException(e.getMessage());
		}
		return mongoTemplate;

	}

	public @Bean() INumberFreqRepo numberFrequencyRepository() throws Exception {
		NumberFrequencyRepository numberFrequencyRepository = null;
		try {
			numberFrequencyRepository = new NumberFrequencyRepository(
					mongoTemplate());
		} catch (Exception e) {
			throw new MongoDbException(e.getMessage());
		}

		return numberFrequencyRepository;
	}

}
