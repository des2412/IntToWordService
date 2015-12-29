package org.desz.inttoword.repository.mongo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.desz.domain.mongodb.NumberFrequency;
import org.desz.inttoword.config.repo.TestRepoConfig;
import org.desz.inttoword.repository.IntFreqRepoJpaRepository;
import org.desz.inttoword.repository.IntFreqRepoJpaRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author des test lifecycle of NumberFrequency entity
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@PropertySource(value = { "classpath:test.mongo.properties" })
@ContextConfiguration(classes = { TestRepoConfig.class })
@ActiveProfiles({ "dev" })
public class TestIntFreqRepo {

	protected final Logger log = Logger.getLogger(TestIntFreqRepo.class.getName());
	@Autowired
	private MongoTemplate mongoTemplate;
	private IntFreqRepoJpaRepository intFreqRepo;

	private String id = "1";
	private final String idNot = "-1x";

	@Autowired
	private String restApi;

	private NumberFrequency numberFreq;

	@Before
	public void init() throws UnknownHostException {
		log.info(String.format("Database Name : %s", mongoTemplate.getDb().getName()));

		intFreqRepo = new IntFreqRepoJpaRepositoryImpl(mongoTemplate, restApi);
		numberFreq = new NumberFrequency("1", 1);
		mongoTemplate.remove(new Query(Criteria.where("number").is("1")), NumberFrequency.class);

	}

	@Test
	public void testSocket() {

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

}
