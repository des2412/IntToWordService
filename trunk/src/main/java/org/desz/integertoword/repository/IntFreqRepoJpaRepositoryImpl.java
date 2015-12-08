package org.desz.integertoword.repository;

import static com.mongodb.client.model.Filters.*;

import com.mongodb.client.result.UpdateResult;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.bson.Document;
import org.desz.domain.mongodb.NumberFrequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

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

	private final String dbUri;

	private final String dbHttps;

	@Autowired()
	public IntFreqRepoJpaRepositoryImpl(MongoOperations mongoOps, String dbUri, String dbHttps) {

		this.mongoOps = mongoOps;
		this.dbUri = dbUri;
		this.dbHttps = dbHttps;
		LOGGER.info(String.format("Mongo URI : %s", dbUri));
		LOGGER.info(String.format("Mongo URI : %s", dbHttps));

	}

	@PostConstruct()
	public void chkConn() {
		ProcessBuilder pb = new ProcessBuilder("curl", dbHttps);
		Process proc = null;
		InputStream is = null;
		try {
			proc = pb.start();
			is = proc.getInputStream();
			LineIterator itr = IOUtils.lineIterator(is, Charset.forName("UTF-8"));
			String s = itr.nextLine();
			if (Objects.nonNull(s) & (s.contains("number_freq") | s.contains("test_number_freq")))
				initOk = true;

		} catch (IOException e) {
			LOGGER.severe("Todo");
		} finally {
			IOUtils.closeQuietly(is);
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
		return mongoOps.findAll(NumberFrequency.class).size();
	}

	@Override
	public <S extends NumberFrequency> S save(S entity) {
		mongoOps.save(entity);
		return (S) findOne(entity.getId());
	}

	@Override
	public NumberFrequency findOne(String id) {
		Query query = new Query(Criteria.where("number").is(id));
		return mongoOps.findOne(query, NumberFrequency.class);
	}

	@Override
	public boolean exists(String id) {
		if (Objects.nonNull(findOne(id)))
			return true;
		return false;
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
		LOGGER.info(String.format("Drop Collection [name], %s", name));
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
	public void deleteAll() {

	}

}
