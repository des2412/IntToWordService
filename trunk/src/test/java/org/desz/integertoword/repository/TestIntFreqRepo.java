package org.desz.integertoword.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.desz.domain.mongodb.NumberFrequency;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	private final String idNot = "-1";
	private String testColn = "numberFrequencies";

	@Before
	public void init() throws UnknownHostException {
		LOGGER.info("Database Name:" + mongoTemplate.getDb().getName());
		intFreqRepo = new IntFreqRepoJpaRepositoryImpl(mongoTemplate);
		intFreqRepo.createCollection(testColn);

	}

	@After
	public void after() {
		intFreqRepo.deleteAllInCollection(testColn);

	}

	@Test
	public void testExists() {
		assertTrue(intFreqRepo.exists(id));
		assertFalse(intFreqRepo.exists(idNot));
	}

	@Test
	public void testFindOne() {
		intFreqRepo.saveOrUpdateFrequency(id);
		assertNotNull(intFreqRepo.findOne(id));
	}

	@Test
	public void testSaveOrUpdate() {

		final int num = 2;
		NumberFrequency nf = intFreqRepo.findOne(id);
		LOGGER.info("Number Frequency:" + nf.toString());
		nf.setCount(num);
		intFreqRepo.saveOrUpdateFrequency(id);
		assertEquals(num, intFreqRepo.findOne(id).getCount());

	}

}
