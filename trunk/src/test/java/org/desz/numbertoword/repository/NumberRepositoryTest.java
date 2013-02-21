package org.desz.numbertoword.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.desz.domain.NumberFrequency;
import org.desz.spring.config.NumberFrequencyRepositoryConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ComponentScan(basePackages = { "org.desz.spring.config",
		"org.desz.numbertoword.repository" })
public class NumberRepositoryTest {

	NumberFrequencyRepository numberFrequencyRepository;

	ApplicationContext ctx;

	@Before
	public void init() {
		ctx = new AnnotationConfigApplicationContext(
				NumberFrequencyRepositoryConfig.class);
		numberFrequencyRepository = (NumberFrequencyRepository) ctx
				.getBean("numberFrequencyRepository");
	}

	@Test
	public void testGetCollection() {

		DBCollection col = numberFrequencyRepository.assignCollection();
		assertNotNull(col);
	}

	@Test
	public void testInsert() {
		numberFrequencyRepository.saveNumberFrequency("100");
	}

	@Test
	public void testGetFrequency() {
		assertNotNull(numberFrequencyRepository.findIntegerFrequency("100"));
	}

	@Test
	public void testUpdateCount() {

		String num = "100";

		BasicDBObject obj = new BasicDBObject();
		obj.put("number", num);
		numberFrequencyRepository.assignCollection().remove(obj);

		numberFrequencyRepository.saveNumberFrequency(num);

		NumberFrequency f = numberFrequencyRepository.findIntegerFrequency(num);

		assertEquals(1, f.getCount());

		numberFrequencyRepository.saveNumberFrequency(num);
		f = numberFrequencyRepository.findIntegerFrequency(num);
		assertEquals(2, f.getCount());

	}

	@Test
	public void testServiceAutowire() {
		// INumberToWordService service = n
	}

}
