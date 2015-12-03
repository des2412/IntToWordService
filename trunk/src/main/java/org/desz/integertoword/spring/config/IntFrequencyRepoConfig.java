package org.desz.integertoword.spring.config;

import javax.inject.Inject;

import org.desz.integertoword.repository.IFIntFreqRepo;
import org.desz.integertoword.repository.IntFreqRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

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
			db = new SimpleMongoDbFactory(new MongoClient(env.getProperty("mongo.db")), "number_freq");
		} catch (Exception e) {
			return null;
		}
		return db;
	}

	public @Bean() IFIntFreqRepo intFreqRepo() {
		IntFreqRepo repo = null;
		if (dbFactory() == null)
			return null;
		MongoTemplate mongoTemplate = new MongoTemplate(dbFactory());
		return new IntFreqRepo(mongoTemplate);
	}

}
