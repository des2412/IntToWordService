package org.desz.integertoword.config.repo;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.desz.integertoword.repository.mongo.TestIntFreqRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
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
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;

@Configuration()
@PropertySource(value = { "classpath:test.mongo.properties" })
@EnableMongoRepositories(basePackageClasses = { TestIntFreqRepo.class })
public class TestRepoConfig extends AbstractMongoConfiguration {

	protected final Logger LOGGER = Logger.getLogger(TestRepoConfig.class.getName());
	@Inject
	private Environment env;

	/*
	 * @Bean public String dbUri() { return env.getProperty("db.url"); }
	 */

	@Bean
	public String dbRestUrl() {
		return env.getProperty("mongolab.rest.api");
	}

	@Override
	protected String getDatabaseName() {
		return env.getProperty("db.id");
	}

	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(), getDatabaseName());
	}

	public @Bean MongoTemplate mongoTemplate() throws Exception {

		return new MongoTemplate(mongoDbFactory());
	}

	@Override
	public Mongo mongo() throws Exception {
		MongoClientURI uri = new MongoClientURI(env.getProperty("mongodb.uri"));
		return new MongoClient(uri);
	}
}