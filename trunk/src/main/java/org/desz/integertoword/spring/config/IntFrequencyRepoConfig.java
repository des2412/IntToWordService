package org.desz.integertoword.spring.config;

import javax.inject.Inject;

import org.desz.integertoword.repository.IntFreqRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
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
public class IntFrequencyRepoConfig {

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

	public @Bean() IntFreqRepo intFreqRepo() {
		IntFreqRepo repo = null;
		try {
			MongoTemplate mongoTemplate = new MongoTemplate(dbFactory());
			repo = new IntFreqRepo(mongoTemplate);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		return repo;
	}

}
