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
import org.desz.inttoword.language.LangContent.PROV_LANG;
import org.desz.inttoword.mapper.Int2StrConverter;
import org.desz.inttoword.repository.IntFreqRepoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author des Update conversion stats if possible.
 * 
 */
@Service
public final class ConversionSrv implements IConverterService<BigInteger> {

	private static final String MSG = "Service requires non-null parameters";

	protected final Logger log = LoggerFactory.getLogger(ConversionSrv.class);

	private final Optional<IntFreqRepoJpaRepository> optFreqRepo;

	private final Int2StrConverter converter;

	@Autowired
	public ConversionSrv(Optional<IntFreqRepoJpaRepository> optFreqRepoSrv, Int2StrConverter converter) {
		this.optFreqRepo = optFreqRepoSrv;
		this.converter = converter;
	}

	/**
	 * Executes conversion to word.
	 */
	@Override
	public String getWordInLang(PROV_LANG provLang, String num) throws IntToWordServiceException {
		num = Objects.requireNonNull(num, MSG);
		provLang = Objects.requireNonNull(provLang, MSG);
		if (!provLang.isValid())
			throw new IntToWordServiceException("Invalid language specified");
		if (optFreqRepo.isPresent()) {
			log.info(String.format("saving %s", num));
			optFreqRepo.get().save(new NumberFrequency(num));// todo change to
																// String
		} else
			log.info("repository connection not permissible");

		// converter = new Int2StrConverter(provLang);
		try {
			return converter.funcIntToString(Integer.parseInt(num), provLang);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new IntToWordServiceException(e.getLocalizedMessage());
		}

	}

}
