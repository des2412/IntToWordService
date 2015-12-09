package org.desz.integertoword.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.desz.domain.mongodb.NumberFrequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

	// private final MongoOperations mongoOps;
	private final MongoTemplate mongoOps;

	private boolean initOk = false;

	private final String dbUri;

	private final String dbRestUrl;

	@Autowired()
	public IntFreqRepoJpaRepositoryImpl(MongoOperations mongoOps, String dbUri, String dbHttps) {

		this.mongoOps = (MongoTemplate) mongoOps;
		this.dbUri = dbUri;// TODO dont need this
		this.dbRestUrl = dbHttps;
		LOGGER.info(String.format("Mongo URI : %s", dbUri));
		LOGGER.info(String.format("Mongo REST URL : %s", dbRestUrl));

	}

	@Override
	public boolean isAvailable() {
		final String dbNme = mongoOps.getDb().getName();

		// create PING request to URI
		String[] arr = dbRestUrl.split("\\?");
		StringBuilder sb = new StringBuilder();
		sb.append(arr[0]).append("/" + dbNme + "/ping?").append(arr[1]);
		// LOGGER.info("CURL PING" + sb.toString());

		ProcessBuilder pb = new ProcessBuilder("curl", dbRestUrl);
		Process proc = null;
		InputStream is = null;
		try {
			proc = pb.start();
			is = proc.getInputStream();
			final String s = IOUtils.lineIterator(is, Charset.forName("UTF-8")).toString();

			if (StringUtils.isNotEmpty(s) & StringUtils.contains(s, "200 OK"))
				return true;

		} catch (IOException e) {
			LOGGER.severe("Todo");
			return false;
		} finally {
			IOUtils.closeQuietly(is);
		}
		return false;

	}

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
		if (isAvailable())
			return save(iter);
		return null;
	}

	@Override
	public Page<NumberFrequency> findAll(Pageable arg0) {
		return null;
	}

	@Override
	public long count() {
		if (isAvailable())
			return mongoOps.findAll(NumberFrequency.class).size();
		return -1;
	}

	@Override
	public <S extends NumberFrequency> S save(S entity) {
		if (isAvailable()) {
			mongoOps.save(entity);
			return (S) findOne(entity.getId());
		}
		return entity;
	}

	@Override
	public NumberFrequency findOne(String id) {
		if (isAvailable()) {
			Query query = new Query(Criteria.where("number").is(id));
			return mongoOps.findOne(query, NumberFrequency.class);
		}
		return null;
	}

	@Override
	public boolean exists(String id) {
		if (!isAvailable())
			return false;
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
		if (Objects.nonNull(findOne(id)))
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
