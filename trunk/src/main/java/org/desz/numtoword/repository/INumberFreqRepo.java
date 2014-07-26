package org.desz.numtoword.repository;

import org.desz.domain.NumberFrequency;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface INumberFreqRepo extends MongoRepository<NumberFrequency, String> {

	void saveNumberFrequency(String string);

	boolean containsNumberFrequency(String num);

	NumberFrequency findNumberFrequency(String num);

	void removeNumberFrequency(String num);

}
