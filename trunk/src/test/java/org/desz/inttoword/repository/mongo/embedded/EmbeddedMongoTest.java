package org.desz.inttoword.repository.mongo.embedded;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import org.desz.domain.mongodb.NumberFrequency;
import org.desz.inttoword.config.repo.EmbeddedConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.WriteResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { EmbeddedConfig.class })
public class EmbeddedMongoTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	protected final Logger LOGGER = Logger.getLogger(EmbeddedMongoTest.class.getName());

	private Query query;

	private NumberFrequency numberFreq;

	@Before
	public void init() {
		numberFreq = new NumberFrequency("1", 1);
		query = new Query(Criteria.where("number").is("1"));
		mongoTemplate.remove(query, NumberFrequency.class);
	}

	@Test
	public void testSave() {
		mongoTemplate.save(numberFreq);
		LOGGER.info(String.format("Save, %s ", numberFreq));

		final NumberFrequency nf = mongoTemplate.findOne(query, NumberFrequency.class);
		assertNotNull("NULL Unexpected", nf);
		LOGGER.info(String.format("Number Freq findOne %s", nf.toString()));
		assertEquals("NUMBER expected should be 1", "1", nf.getNumber());
	}

	@Test
	public void testUpdate() {
		mongoTemplate.save(numberFreq);

		WriteResult wrtRes = mongoTemplate.updateFirst(query, Update.update("count", numberFreq.getCount() + 1),
				NumberFrequency.class);
		assertEquals(1, wrtRes.getN());
		final NumberFrequency nf = mongoTemplate.findOne(query, NumberFrequency.class);
		assertEquals("COUNT expected should be 2", Integer.valueOf(2), nf.getCount());

	}

	@Test
	public void testFindAll() {
		mongoTemplate.save(numberFreq);
		assertEquals(1, mongoTemplate.findAll(NumberFrequency.class).size());
		mongoTemplate.remove(query, NumberFrequency.class);
		assertEquals(0, mongoTemplate.findAll(NumberFrequency.class).size());

	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
		mongoTemplate.getDb().getMongo().close();

	}
}
