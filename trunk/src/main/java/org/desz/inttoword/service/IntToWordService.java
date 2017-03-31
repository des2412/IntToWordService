/**
 * 
 */
package org.desz.inttoword.service;

import java.util.Objects;
import java.util.Optional;

import org.desz.domain.mongodb.NumberFrequency;
import org.desz.inttoword.exceptions.AppConversionException;
import org.desz.inttoword.exceptions.IntToWordServiceException;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import org.desz.inttoword.mapper.ConversionService;
import org.desz.inttoword.repository.IntFreqRepoJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author des. converts Integer, 0 to Integer.MAX_VALUE, to word.
 * 
 */
@Service
public final class IntToWordService implements INumberToWordService {

	private static final String MSG = "Service requires non-null parameters";

	protected final Logger log = LoggerFactory
			.getLogger(IntToWordService.class);

	private final Optional<IntFreqRepoJpaRepository> optFreqRepo;

	private final ConversionService conversionWorker;

	/**
	 * 
	 * @param optFreqRepoSrv
	 *            Optional containing JpaRepository or empty if connection is
	 *            not possible.
	 * @param conversionWorker
	 */
	@Autowired
	public IntToWordService(Optional<IntFreqRepoJpaRepository> optFreqRepoSrv,
			ConversionService conversionWorker) {
		this.optFreqRepo = optFreqRepoSrv;
		this.conversionWorker = conversionWorker;
	}

	@Override
	public String getWordInLang(ProvLang provLang, String num)
			throws IntToWordServiceException {
		num = Objects.requireNonNull(num, MSG);
		provLang = Objects.requireNonNull(provLang, MSG);
		if (!provLang.isValid())
			throw new IntToWordServiceException("Invalid language specified");

		if (optFreqRepo.isPresent()) {
			log.info(String.format("Saving %s to persistence repository", num));
			optFreqRepo.get().save(new NumberFrequency(num));
		} else
			log.info("repository connection unavailable");

		try {
			return conversionWorker.convertIntToWord(Integer.parseInt(num),
					provLang);
		} catch (AppConversionException e) {
			log.error(e.getMessage());
			throw new IntToWordServiceException(e.getLocalizedMessage());
		}

	}

}
