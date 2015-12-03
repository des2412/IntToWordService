package org.desz.integertoword.repository;

import org.desz.domain.mongodb.NumberFrequency;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IFIntFreqRepo extends MongoRepository<NumberFrequency, String> {

	void saveOrUpdateFrequency(String string);

	boolean isAvailable();

}
