package org.desz.integertoword.repository.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.desz.domain.mongodb.NumberFrequency;
import org.desz.integertoword.config.repo.TestRepoConfig;
import org.desz.integertoword.repository.IntFreqRepoJpaRepository;
import org.desz.integertoword.repository.IntFreqRepoJpaRepositoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.client.MongoDatabase;

/**
 * @author des test lifecycle of NumberFrequency entity
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@PropertySource(value = { "classpath:test.mongo.properties" })
@ContextConfiguration(classes = { TestRepoConfig.class })
public class TestIntFreqRepo {

	protected final Logger LOGGER = Logger.getLogger(TestIntFreqRepo.class.getName());
	@Autowired
	MongoTemplate mongoTemplate;
	private IntFreqRepoJpaRepository intFreqRepo;

	private String id = "1";
	private final String idNot = "-1x";
	@Autowired
	private String dbUri;

	@Autowired
	private String dbHttps;

	private Query query;

	private NumberFrequency numberFreq;

	@Before
	public void init() throws UnknownHostException {
		LOGGER.info("Database Name:" + mongoTemplate.getDb().getName());
		intFreqRepo = new IntFreqRepoJpaRepositoryImpl(mongoTemplate, dbUri, dbHttps);
		numberFreq = new NumberFrequency("1", 1);
		query = new Query(Criteria.where("number").is("1"));
		mongoTemplate.remove(query, NumberFrequency.class);

	}

	@Test
	public void testExists() {
		if (intFreqRepo.isAvailable()) {

			intFreqRepo.save(numberFreq);
			assertTrue(intFreqRepo.exists(id));
			assertFalse(intFreqRepo.exists(idNot));
		}
	}

	@Test
	public void testFindOne() {
		if (intFreqRepo.isAvailable()) {
			intFreqRepo.save(numberFreq);
			assertNotNull(intFreqRepo.findOne(id));
		}
	}

	public void finalize() throws Throwable {
		super.finalize();
		mongoTemplate.getDb().getMongo().close();

	}
}
