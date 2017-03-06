/**
 * 
 */
package org.desz.inttoword.service;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.desz.domain.mongodb.NumberFrequency;
import org.desz.inttoword.exceptions.IntToWordServiceException;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import org.desz.inttoword.mapper.ConversionWorker;
import org.desz.inttoword.repository.IntFreqRepoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author des Update conversion stats if possible.
 * 
 */
@Service
public final class IntToWordService implements IntoWordServiceInterface<BigInteger> {

	private static final String MSG = "Service requires non-null parameters";

	protected final Logger log = LoggerFactory.getLogger(IntToWordService.class);

	private final Optional<IntFreqRepoJpaRepository> optFreqRepo;

	private final ConversionWorker converter;

	@Autowired
	public IntToWordService(Optional<IntFreqRepoJpaRepository> optFreqRepoSrv, ConversionWorker converter) {
		this.optFreqRepo = optFreqRepoSrv;
		this.converter = converter;
	}

	/**
	 * Executes conversion to word.
	 */
	@Override
	public String getWordInLang(ProvLang provLang, String num) throws IntToWordServiceException {
		num = Objects.requireNonNull(num, MSG);
		provLang = Objects.requireNonNull(provLang, MSG);
		if (!provLang.isValid())
			throw new IntToWordServiceException("Invalid language specified");
		if (optFreqRepo.isPresent()) {
			log.info(String.format("saving %s", num));
			optFreqRepo.get().save(new NumberFrequency(num));
		} else
			log.info("repository connection not permissible");

		try {
			return converter.convertIntToWord(Integer.parseInt(num), provLang);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new IntToWordServiceException(e.getLocalizedMessage());
		}

	}

}
