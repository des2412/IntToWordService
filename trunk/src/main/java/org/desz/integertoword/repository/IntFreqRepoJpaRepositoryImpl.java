package org.desz.integertoword.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.desz.domain.mongodb.NumberFrequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * MongoRepository. Non-implemented methods return null/do nothing.
 * 
 * @author des
 *
 */
@Repository
public class IntFreqRepoJpaRepositoryImpl implements IntFreqRepoJpaRepository {

	protected final Logger LOGGER = Logger.getLogger(IntFreqRepoJpaRepositoryImpl.class.getName());

	private final MongoOperations mongoOps;

	private boolean initOk = false;

	@Autowired(required = false)
	public IntFreqRepoJpaRepositoryImpl(MongoOperations mongoOps) {

		this.mongoOps = mongoOps;

	}

	@PostConstruct()
	public void chkConn() {
		ProcessBuilder pb = new ProcessBuilder("curl",
				"https://api.mongolab.com/api/1/databases?apiKey=ACnducluV0wsD8N1GBVkJT9p531BqwNd");
		Process proc;
		try {
			proc = pb.start();
			LineIterator itr = IOUtils.lineIterator(proc.getInputStream(), Charset.forName("UTF-8"));
			String s = itr.nextLine();
			if (s != null & s.contains("number_freq"))
				initOk = true;

		} catch (IOException e) {
			LOGGER.severe("Todo");
		}

	}

	/*
	 * @Override public boolean isAvailable() {
	 * 
	 * DBObject ping = new BasicDBObject("ping", "1"); if
	 * (mongoOps.executeCommand(ping).ok()) return true;
	 * 
	 * return false;
	 * 
	 * }
	 */

	@Query
	@Override
	public void saveOrUpdateFrequency(final String num) {

		if (initOk) {
			if (exists(num)) {
				NumberFrequency nf = findOne(num);
				nf.incrementCount();
				mongoOps.updateFirst(query(where("number").is(num)), update("count", nf.getCount()),
						NumberFrequency.class);
				return;
			}
			// insert new NumberFrequency with count 1
			mongoOps.insert(new NumberFrequency(num, 1));
		}

	}

	@Query
	@Override
	public List<NumberFrequency> findAll() {
		return null;
	}

	@Override
	public List<NumberFrequency> findAll(Sort sort) {
		return findAll(sort);
	}

	@Override
	public <S extends NumberFrequency> List<S> save(Iterable<S> iter) {
		return save(iter);
	}

	@Override
	public Page<NumberFrequency> findAll(Pageable arg0) {
		return null;
	}

	@Override
	public long count() {
		return count();
	}

	@Override
	public <S extends NumberFrequency> S save(S entity) {
		return save(entity);
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
		if (mongoOps.findById(id, NumberFrequency.class) == null)
			mongoOps.remove(query(where("number").is(id)), NumberFrequency.class);

	}

	@Override
	public void delete(NumberFrequency entity) {
		mongoOps.remove(entity);

	}

	@Override
	public void delete(Iterable<? extends NumberFrequency> entities) {

	}

	@Override
	public void deleteAllInCollection(String name) {
		LOGGER.info(String.format("Dropping Collection Named, %s", name));
		mongoOps.getCollection(name).drop();

	}

	@Override
	public <S extends NumberFrequency> S insert(S arg0) {
		return insert(arg0);
	}

	@Override
	public <S extends NumberFrequency> List<S> insert(Iterable<S> arg0) {
		return null;
	}

	@Override
	public void createCollection(String name) {
		mongoOps.createCollection(name);
	}

	@Override
	public void deleteAll() {

	}

}
