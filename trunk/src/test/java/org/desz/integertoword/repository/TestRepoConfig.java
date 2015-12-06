package org.desz.integertoword.repository;

import java.util.logging.Logger;

import javax.inject.Inject;

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
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;

@Configuration()
@PropertySource(value = { "classpath:test.mongo.properties" })
@EnableMongoRepositories(basePackageClasses = { TestIntFreqRepo.class })
public class TestRepoConfig extends AbstractMongoConfiguration {

	protected final Logger LOGGER = Logger.getLogger(TestRepoConfig.class.getName());
	@Inject
	private Environment env;

	@Override
	protected String getDatabaseName() {
		return env.getProperty("db.id");
	}

	@Override
	public Mongo mongo() throws Exception {
		MongoClientURI uri = new MongoClientURI(env.getProperty("mongo.db"));
		MongoClient client = new MongoClient(uri);
		// TODO check ping or better look at Restful API provided to check
		// availability. Caution provider specific API..
		client.setReadPreference(ReadPreference.nearest());
		client.setWriteConcern(WriteConcern.SAFE);
		return client;
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), getDatabaseName());

	}
}
