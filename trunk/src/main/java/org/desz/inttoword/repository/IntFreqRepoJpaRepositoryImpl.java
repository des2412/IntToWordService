package org.desz.inttoword.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

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

/**
 * MongoRepository. Non-implemented methods return null/do nothing.
 * 
 * @author des
 *
 */
@Repository
public class IntFreqRepoJpaRepositoryImpl implements IntFreqRepoJpaRepository {

	protected final Logger log = Logger.getLogger(IntFreqRepoJpaRepositoryImpl.class.getName());

	private final MongoTemplate mongoOps;

	private final String dbRestUrl;

	/**
	 * 
	 * @param mongoOps
	 *            the MongoOps.
	 * @param dbRestApi
	 *            the resource URI.
	 */
	@Autowired()
	public IntFreqRepoJpaRepositoryImpl(MongoOperations mongoOps, String dbRestApi) {

		this.mongoOps = (MongoTemplate) mongoOps;
		this.dbRestUrl = dbRestApi;

	}

	@Override
	public boolean isAvailable() {
		// create Reactive request to URI
		String[] arr = dbRestUrl.split("\\?");
		// invoke response
		Future<Response> response = ClientBuilder.newClient()
				.target(arr[0] + "/" + mongoOps.getDb().getName() + "/ping?" + arr[1]).request().async().get();
		try {
			return (response.get(3, TimeUnit.SECONDS).getStatus() == 200);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			return false;
		}

	}

	@Override
	public List<NumberFrequency> findAll() {
		return null;
	}

	@Override
	public List<NumberFrequency> findAll(Sort sort) {
		return (mongoOps.findAll(NumberFrequency.class));
	}

	@Override
	public <S extends NumberFrequency> List<S> save(Iterable<S> iter) {
		if (isAvailable())
			mongoOps.save(iter);
		return (List<S>) iter;
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
	public <S extends NumberFrequency> S insert(S entity) {
		save(entity);
		return (S) findOne(entity.getId());
	}

	@Override
	public <S extends NumberFrequency> List<S> insert(Iterable<S> arg0) {
		return null;
	}

	@Override
	public void deleteAll() {

	}

}
