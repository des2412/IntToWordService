package org.desz.integertoword.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.desz.domain.mongodb.NumberFrequency;
import org.desz.integertoword.spring.config.IntFrequencyRepoConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author des test lifecycle of NumberFrequency entity
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@PropertySource(value = { "classpath:test.mongo.properties" })
@ContextConfiguration(classes = { TestIntFrequencyRepoConfig.class })
// @ComponentScan(basePackages = { "org.desz.integertoword.repository" })
public class TestIntFreqRepo {

	protected final Logger LOGGER = Logger.getLogger(TestIntFreqRepo.class
			.getName());
	private INumberFreqRepo intFreqRepo;

	private String id = "100";

	private AnnotationConfigApplicationContext annoCtx;

	@Before
	public void init() throws UnknownHostException {
		
		annoCtx = new AnnotationConfigApplicationContext();
		annoCtx.register(IntFrequencyRepoConfig.class);
		annoCtx.register(TestIntFrequencyRepoConfig.class);
		annoCtx.refresh();

		MongoDbFactory fac = annoCtx.getBean(MongoDbFactory.class);
		
		LOGGER.info("Database Name:" + fac.getDb().getName());

		try {
			MongoTemplate mongoTemplate = new MongoTemplate(fac);
			intFreqRepo = new IntFreqRepo(mongoTemplate);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		intFreqRepo.saveOrUpdateFrequency(id);

	}

	@After
	public void after() {
		intFreqRepo.delete(id);
		annoCtx.close();

	}

	@Test
	public void testAvailable() {

		assertTrue(intFreqRepo.isAvailable());
	}

	@Test
	public void testExists() {
		assertTrue(intFreqRepo.exists(id));
	}

	@Test
	public void testFindOne() {
		assertNotNull(intFreqRepo.findOne(id));
	}

	@Test
	public void testSaveOrUpdateFrequency() {

		int num = 2;
		NumberFrequency nf = intFreqRepo.findOne(id);
		nf.setCount(num);
		intFreqRepo.saveOrUpdateFrequency(id);
		assertEquals(2, intFreqRepo.findOne(id).getCount());

	}

}
