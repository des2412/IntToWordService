package org.desz.inttoword.config;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.desz.inttoword.repository.IntFreqRepoJpaRepository;
import org.desz.inttoword.repository.IntFreqRepoJpaRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
 * Configure Mongo database.
 * 
 * @author des
 * 
 */
@Configuration()
@Profile({ "cloud", "dev" })
@EnableMongoRepositories()
@PropertySource(value = { "classpath:mongo.properties" })
public class IntFreqRepoConfig extends AbstractMongoConfiguration {
	protected final Logger log = Logger.getLogger(IntFreqRepoConfig.class.getName());
	@Inject
	private Environment env;

	@Profile({ "dev" })
	@Bean
	public String dbUri() {
		return env.getProperty("db.url");
	}

	@Profile({ "dev" })
	@Bean
	public String restApi() {
		return env.getProperty("mongolab.rest.api");
	}

	@Profile({ "dev" })
	@Override
	protected String getDatabaseName() {
		return env.getProperty("db.id");
	}

	@Profile({ "dev" })
	@Override
	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(), getDatabaseName());
	}

	@Profile({ "dev" })
	public @Bean(name = "dev_repo") IntFreqRepoJpaRepository intFreqRepo() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongo(), getDatabaseName());
		return new IntFreqRepoJpaRepositoryImpl(mongoTemplate, env.getProperty("mongolab.rest.api"));
	}

	@Profile("cloud")
	public @Bean(name = "cloudrepo") String emp() throws Exception {
		// prevent connection attempt to Mongo for google app engine.
		return "";
	}

	@Profile({ "dev" })
	@Override
	public @Bean MongoTemplate mongoTemplate() throws Exception {

		return new MongoTemplate(mongoDbFactory());
	}

	@Override
	public Mongo mongo() throws Exception {

		MongoClientURI clientUri = new MongoClientURI(env.getProperty("mongo.db.uri"));
		return new MongoClient(clientUri);

	}

}
