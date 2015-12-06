package org.desz.integertoword.spring.config;

import javax.inject.Inject;

import org.desz.integertoword.repository.IntFreqRepoJpaRepository;
import org.desz.integertoword.repository.IntFreqRepoJpaRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;

/**
 * Configuration for Mongo document database
 * 
 * @author des
 * 
 */
@Configuration()
@EnableMongoRepositories()
@PropertySource(value = { "classpath:mongo.properties" })
public class IntFreqRepoConfig extends AbstractMongoConfiguration {

	@Inject
	private Environment env;

	public @Bean() IntFreqRepoJpaRepository intFreqRepo() throws Exception {
		if (mongo() == null)
			return null;
		MongoTemplate mongoTemplate = new MongoTemplate(mongo(), getDatabaseName());
		return new IntFreqRepoJpaRepositoryImpl(mongoTemplate);
	}

	@Override
	protected String getDatabaseName() {
		return env.getProperty("db.id");
	}

	@Override
	public Mongo mongo() throws Exception {

		try {

			MongoClientURI uri = new MongoClientURI(env.getProperty("mongo.db.uri"));
			MongoClient client = new MongoClient(uri);
			client.setWriteConcern(WriteConcern.SAFE);
			return client;

		} catch (Exception e) {
			return null;
		}
	}

}
