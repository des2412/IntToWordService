/**
 * 
 */
package org.desz.integertoword.service;

import java.math.BigInteger;
import java.util.Optional;
import java.util.logging.Logger;

import org.desz.domain.mongodb.NumberFrequency;
import org.desz.integertoword.content.ContentContainer.PROV_LANG;
import org.desz.integertoword.exceptions.IntToWordServiceException;
import org.desz.integertoword.mapper.Converter;
import org.desz.integertoword.repository.IntFreqRepoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author des Service Stereotype; update MongoDb and invokes the integer
 *         conversion to word.
 * 
 */
@Service
public final class IntToWordServiceImpl implements IFIntToWordService<BigInteger> {

	protected final Logger LOGGER = Logger.getLogger(IntToWordServiceImpl.class.getName());

	private final Optional<IntFreqRepoJpaRepository> optFreqRepo;

	@Autowired
	public IntToWordServiceImpl(Optional<IntFreqRepoJpaRepository> optFreqRepoSrv) {
		this.optFreqRepo = optFreqRepoSrv;
		if (optFreqRepo.isPresent())
			LOGGER.info("Mongo Repo Service Available");
		else
			LOGGER.info("Mongo Repo Service Unavailable");
	}

	@Override
	public String getWordInLang(PROV_LANG provLang, String num) throws IntToWordServiceException {
		if (provLang.equals(PROV_LANG.EMPTY))
			throw new IntToWordServiceException("Empty provisioned language specified");
		if (optFreqRepo.isPresent())
			optFreqRepo.get().save(new NumberFrequency(num));// todo create
																// method so no
																// dep on
																// NumberFreq.
		else
			LOGGER.info("repository unavailable - stats will not be collected");
		Converter converter = new Converter(provLang);
		try {
			return converter.convert(Integer.parseInt(num)).toLowerCase();
		} catch (Exception e) {
			throw new IntToWordServiceException(e.getLocalizedMessage());
		}

	}

}
