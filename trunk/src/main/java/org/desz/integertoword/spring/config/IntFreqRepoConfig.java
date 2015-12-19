package org.desz.integertoword.spring.config;

import javax.inject.Inject;

import org.desz.integertoword.repository.IntFreqRepoJpaRepository;
import org.desz.integertoword.repository.IntFreqRepoJpaRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

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

	@Bean
	public String dbUri() {
		return env.getProperty("db.url");
	}

	@Bean
	public String restApi() {
		return env.getProperty("mongolab.rest.api");
	}

	@Override
	protected String getDatabaseName() {
		return env.getProperty("db.id");
	}

	@Override
	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(), getDatabaseName());
	}

	public @Bean() IntFreqRepoJpaRepository intFreqRepo() throws Exception {
		if (mongo() == null)
			return null;
		MongoTemplate mongoTemplate = new MongoTemplate(mongo(), getDatabaseName());
		return new IntFreqRepoJpaRepositoryImpl(mongoTemplate, env.getProperty("mongolab.rest.api"));
	}

	@Override
	public @Bean MongoTemplate mongoTemplate() throws Exception {

		return new MongoTemplate(mongoDbFactory());
	}

	@Override
	public Mongo mongo() throws Exception {
		MongoClientURI connectionString = new MongoClientURI(env.getProperty("mongo.db.uri"));
		return new MongoClient(connectionString);
	}

}
