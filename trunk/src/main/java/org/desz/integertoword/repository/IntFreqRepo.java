package org.desz.integertoword.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.util.List;
import java.util.logging.Logger;

import org.desz.domain.mongodb.NumberFrequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Partial implementation of MongoRepository. Non-implemented methods return
 * null.
 * 
 * @author des
 *
 */
@Repository
public class IntFreqRepo implements IFIntFreqRepo {

	protected final Logger LOGGER = Logger.getLogger(IntFreqRepo.class.getName());

	private final MongoOperations mongoOps;

	@Autowired()
	public IntFreqRepo(MongoOperations mongoOps) {

		this.mongoOps = mongoOps;
	}

	@Override
	public boolean isAvailable() {

		DBObject ping = new BasicDBObject("ping", "1");
		if (mongoOps.executeCommand(ping).ok())
			return true;

		return false;

	}

	@Override
	public void saveOrUpdateFrequency(final String num) {

		NumberFrequency nf = null;
		if (exists(num)) {
			nf = findOne(num);
			final int cnt = nf.getCount() + 1;
			nf.setCount(cnt);
			mongoOps.updateFirst(query(where("number").is(num)), update("count", cnt), NumberFrequency.class);
			return;
		}
		// insert new NumberFrequency with count 1
		mongoOps.insert(new NumberFrequency(num, 1));

	}

	@Override
	public List<NumberFrequency> findAll() {
		return null;
	}

	@Override
	public List<NumberFrequency> findAll(Sort arg0) {
		return null;
	}

	@Override
	public <S extends NumberFrequency> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<NumberFrequency> findAll(Pageable arg0) {
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends NumberFrequency> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberFrequency findOne(String id) {
		return mongoOps.findById(id, NumberFrequency.class);
	}

	@Override
	public boolean exists(String id) {
		if (mongoOps.findById(id, NumberFrequency.class) == null)
			return false;

		return true;
	}

	@Override
	public Iterable<NumberFrequency> findAll(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		mongoOps.remove(query(where("number").is(id)), NumberFrequency.class);

	}

	@Override
	public void delete(NumberFrequency entity) {
		mongoOps.remove(entity);

	}

	@Override
	public void delete(Iterable<? extends NumberFrequency> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends NumberFrequency> S insert(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends NumberFrequency> List<S> insert(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
