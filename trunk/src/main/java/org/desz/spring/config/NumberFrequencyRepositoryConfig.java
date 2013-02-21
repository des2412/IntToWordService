package org.desz.spring.config;

import org.desz.numbertoword.repository.NumberFrequencyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoURI;

@Configuration()
// @EnableMongoRepositories(basePackages="org.desz.numbertoword.repository")
public class NumberFrequencyRepositoryConfig {

	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		MongoDbFactory db;
		try {
			db = new SimpleMongoDbFactory(
					new MongoURI(
							"mongodb://des:speckle5@linus.mongohq.com:10003/numtowordstats"));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return db;
	}

	public @Bean()
	MongoTemplate mongoTemplate() throws Exception {

		MongoTemplate mongoTemplate;
		try {
			mongoTemplate = new MongoTemplate(mongoDbFactory());

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return mongoTemplate;

	}

	public @Bean()
	NumberFrequencyRepository numberFrequencyRepository() throws Exception {
		NumberFrequencyRepository numberFrequencyRepository = null;
		try {
			numberFrequencyRepository = new NumberFrequencyRepository(
					mongoTemplate());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		// numberFrequencyRepository.setMongoTemplate(mongoTemplate());

		return numberFrequencyRepository;
	}

}
