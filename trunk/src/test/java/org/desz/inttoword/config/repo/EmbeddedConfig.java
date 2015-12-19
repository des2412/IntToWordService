package org.desz.inttoword.config.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;

@Configuration
@PropertySource(value = { "classpath:test.mongo.properties" })
public class EmbeddedConfig extends AbstractMongoConfiguration {

	@Autowired
	private Environment env;

	@Override
	protected String getDatabaseName() {
		return env.getRequiredProperty("db.id");
	}

	@Override
	public Mongo mongo() throws Exception {
		// 2.6.1 config passes, 3.0.7 passes
		return new EmbeddedMongoBuilder().version("3.0.7").bindIp("127.0.0.1").port(12345).build();
	}

	@Override
	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(), getDatabaseName());
	}

	@Override
	public @Bean MongoTemplate mongoTemplate() throws Exception {

		return new MongoTemplate(mongoDbFactory());

	}
}
