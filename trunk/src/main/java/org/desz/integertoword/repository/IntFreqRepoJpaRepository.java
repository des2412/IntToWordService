package org.desz.integertoword.repository;

import org.desz.domain.mongodb.NumberFrequency;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IntFreqRepoJpaRepository extends MongoRepository<NumberFrequency, String> {

	void saveOrUpdateFrequency(String id);

	void createCollection(String name);

	void deleteAllInCollection(String name);

	// boolean isAvailable();

}
