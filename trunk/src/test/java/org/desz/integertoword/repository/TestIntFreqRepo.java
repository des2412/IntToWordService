package org.desz.integertoword.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.desz.domain.mongodb.NumberFrequency;
import org.desz.integertoword.spring.config.IntFrequencyRepoConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.DB;

/**
 * @author des test lifecycle of NumberFrequency entity
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@PropertySource(value = { "classpath:test.mongo.properties" })
@ContextConfiguration(classes = { TestRepoConfig.class })
// @ComponentScan(basePackages = { "org.desz.integertoword.repository" })
public class TestIntFreqRepo {

	protected final Logger LOGGER = Logger.getLogger(TestIntFreqRepo.class.getName());
	private IFIntFreqRepo intFreqRepo;

	private String id = "100";

	private static AnnotationConfigApplicationContext annoCtx;
	private static DB db;
	private static MongoDbFactory mongoFactory;

	@BeforeClass
	public static void beforeClass() {
		annoCtx = new AnnotationConfigApplicationContext();
		annoCtx.register(IntFrequencyRepoConfig.class);
		annoCtx.register(TestRepoConfig.class);
		annoCtx.refresh();
		mongoFactory = annoCtx.getBean(MongoDbFactory.class);

		db = mongoFactory.getDb();
	}

	@Before
	public void init() throws UnknownHostException {

		LOGGER.info("Database Name:" + db.getName());

		try {
			MongoTemplate mongoTemplate = new MongoTemplate(mongoFactory);
			intFreqRepo = new IntFreqRepo(mongoTemplate);
			// db.;
			// db.requestEnsureConnection();
			intFreqRepo.saveOrUpdateFrequency(id);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@AfterClass
	public static void afterClass() {
		annoCtx.close();
	}

	@After
	public void after() {
		intFreqRepo.delete(id);

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
