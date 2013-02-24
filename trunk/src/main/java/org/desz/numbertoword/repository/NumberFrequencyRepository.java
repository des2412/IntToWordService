package org.desz.numbertoword.repository;

import java.util.logging.Logger;

import org.desz.domain.NumberFrequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

@Repository
public class NumberFrequencyRepository {

	protected final static Logger LOGGER = Logger
			.getLogger(NumberFrequencyRepository.class.getName());

	@Autowired()
	public NumberFrequencyRepository(MongoTemplate mongoTemplate) {

		this.mongoTemplate = mongoTemplate;
		this.numFreqColn = assignCollection();
	}

	private DBCollection numFreqColn = null;

	/**
	 * 
	 * @return
	 */
	public DBCollection assignCollection() {

		if (!mongoTemplate.collectionExists(NumberFrequency.class)) {
			numFreqColn = mongoTemplate.createCollection("numberFrequency");
		} else {

			numFreqColn = mongoTemplate.getCollection("numberFrequency");
		}
		numFreqColn.ensureIndex("number");
		return numFreqColn;
	}

	public void saveNumberFrequency(final String num) throws MongoException {

		numFreqColn.setWriteConcern(WriteConcern.JOURNALED);
		NumberFrequency freq = findIntegerFrequency(num);
		BasicDBObject dbObj = new BasicDBObject();

		try {
			if (freq.getNumber() != null) {

				int cnt = freq.getCount();
				BasicDBObject newObj = new BasicDBObject();
				newObj.put("number", freq.getNumber());
				newObj.put("count", ++cnt);
				dbObj.put("number", num);
				numFreqColn.remove(dbObj);
				numFreqColn.insert(newObj);

			} else {

				LOGGER.info("inserting " + num + " with count 1");
				dbObj.put("number", num);
				dbObj.put("count", 1);
				numFreqColn.insert(dbObj);

			}
		} catch (MongoException e) {
			throw new MongoException(e.getMessage());
		}

	}

	public NumberFrequency findIntegerFrequency(String num) {

		DBObject obj = null;

		NumberFrequency freq = new NumberFrequency();

		BasicDBObject keys = new BasicDBObject("number", num);
		obj = numFreqColn.findOne(keys);
		if (obj != null) {
			freq = new NumberFrequency((String) obj.get("number"),
					(Integer) obj.get("count"));
		}

		return freq;
	}

	private MongoTemplate mongoTemplate;
}
