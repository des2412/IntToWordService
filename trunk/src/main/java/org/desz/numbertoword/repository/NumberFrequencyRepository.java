package org.desz.numbertoword.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.util.List;
import java.util.logging.Logger;

import org.desz.domain.NumberFrequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
public class NumberFrequencyRepository implements INumberFreqRepo {

	protected final Logger LOGGER = Logger
			.getLogger(NumberFrequencyRepository.class.getName());

	@Autowired()
	public NumberFrequencyRepository(MongoOperations mongoTemplate) {

		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public boolean containsNumberFrequency(String num) {
		if (mongoTemplate.findById(num, NumberFrequency.class) == null) {
			return false;
		}
		return true;
	}

	@Override
	public void removeNumberFrequency(String num) {
		mongoTemplate.remove(query(where("number").is(num)),
				NumberFrequency.class);
	}

	@Override
	public void saveNumberFrequency(final String num) {

		NumberFrequency freq = null;
		if (containsNumberFrequency(num)) {
			freq = findNumberFrequency(num);
			LOGGER.info("Updating NumberFrequency [current persistent JSON representation]:" + freq.toString());
			final int cnt = freq.getCount() + 1;
			freq.setCount(cnt);
			mongoTemplate.updateFirst(query(where("number").is(num)),
					update("count", cnt), NumberFrequency.class);
			return;
		}

		freq = new NumberFrequency(num, 1);
		mongoTemplate.insert(freq);

	}

	@Override
	public NumberFrequency findNumberFrequency(String num) {

		if (containsNumberFrequency(num)) {

			return mongoTemplate.findById(num, NumberFrequency.class);
		}

		return null;
	}

	private MongoOperations mongoTemplate;

	@Override
	public List<NumberFrequency> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NumberFrequency> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends NumberFrequency> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<NumberFrequency> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(NumberFrequency arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends NumberFrequency> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<NumberFrequency> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberFrequency findOne(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends NumberFrequency> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
