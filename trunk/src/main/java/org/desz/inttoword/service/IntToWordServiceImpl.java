/**
 * 
 */
package org.desz.inttoword.service;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.desz.domain.mongodb.NumberFrequency;
import org.desz.inttoword.exceptions.IntToWordServiceException;
import org.desz.inttoword.language.LangContent.PROV_LANG;
import org.desz.inttoword.mapper.Converter;
import org.desz.inttoword.repository.IntFreqRepoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author des Update conversion stats if possible. Executes conversion to word.
 * 
 */
@Service
public final class IntToWordServiceImpl implements IConverterService<BigInteger> {

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
		num = Objects.requireNonNull(num, "Service requires non-null parameters");
		provLang = Objects.requireNonNull(provLang, "Service requires non-null parameters");
		if (!provLang.isValid())
			throw new IntToWordServiceException("Invalid language specified");
		if (optFreqRepo.isPresent())
			optFreqRepo.get().save(new NumberFrequency(num));// todo create
																// method so no
																// dep on
																// NumberFreq.
		else
			LOGGER.info("repository unavailable - stats will not be collected");
		Converter converter = new Converter(provLang);
		try {
			return converter.funcIntToString(Integer.parseInt(num));// .convert(Integer.parseInt(num)).toLowerCase();
		} catch (Exception e) {
			throw new IntToWordServiceException(e.getLocalizedMessage());
		}

	}

}
