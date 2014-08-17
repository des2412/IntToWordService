package org.desz.integertoword.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.desz.domain.mongodb.NumberFrequency;
import org.desz.integertoword.repository.INumberFreqRepo;
import org.desz.integertoword.spring.config.NumberFrequencyRepositoryConfigTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author des
 * test lifecycle of NumberFrequency entity
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { NumberFrequencyRepositoryConfigTest.class })
@ComponentScan(basePackages = { "org.desz.integertoword.repository" })
public class NumberRepositoryTest {

	@Autowired
	private INumberFreqRepo numberFrequencyRepository;

	@Autowired
	private MongoOperations mongoTemplate;

	private final NumberFrequency nf = new NumberFrequency("100", 1);

	@Before
	public void init() {
		mongoTemplate.insert(nf);

	}

	@After
	public void after() {
		mongoTemplate.remove(nf);
		
	}

	@Test
	public void testLookUpNumberFrequency() {
		assertTrue(numberFrequencyRepository.containsNumberFrequency(nf.getNumber()));
	}

	@Test
	public void testFindNumberFrequency() {
		assertNotNull(numberFrequencyRepository.findNumberFrequency("100"));
	}

	@Test
	public void testSaveNumberFrequency() {

		int num = 2;
		String val = "100";
		NumberFrequency nf = numberFrequencyRepository.findNumberFrequency(val);
		nf.setCount(num);
		numberFrequencyRepository.saveNumberFrequency(val);
		assertEquals(2, numberFrequencyRepository.findNumberFrequency(val)
				.getCount());

	}

}
