/**
 * 
 */
package org.desz.integertoword.service;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.integertoword.content.ContentContainer.PROV_LANG;
import org.desz.integertoword.exceptions.IntToWordServiceException;
import org.desz.integertoword.mapper.RecursiveConverter;
import org.desz.integertoword.repository.IntFreqRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author des Service Stereotype; update MongoDb and invokes the integer
 *         conversion to word.
 * 
 */
@Service
public final class IntToWordServiceImpl implements
		INumberToWordService<BigInteger> {

	protected final Logger LOGGER = Logger.getLogger(IntToWordServiceImpl.class
			.getName());

	private final IntFreqRepo intFreqRepo;

	@Autowired
	public IntToWordServiceImpl(IntFreqRepo numberFrequencyRepository) {
		this.intFreqRepo = numberFrequencyRepository;
	}

	/**
	 * required if platform (ie GAE) connection to MongoDB repository is not
	 * permitted
	 */
	public IntToWordServiceImpl() {
		intFreqRepo = null;
	}

	@Override
	public String getWordInLang(PROV_LANG provLang, String num)
			throws IntToWordServiceException {
		if (provLang.equals(PROV_LANG.EMPTY))
			throw new IntToWordServiceException(
					"Empty provisioned language specified");
		if (intFreqRepo != null & intFreqRepo.isAvailable()) {
			intFreqRepo.saveOrUpdateFrequency(num);
		} else
			LOGGER.info("repository unavailable - stats not collected");
		RecursiveConverter converter = new RecursiveConverter(provLang);
		try {
			return converter.convert(new StringBuilder(), Integer.valueOf(num));
		} catch (Exception e) {
			throw new IntToWordServiceException(e.getLocalizedMessage());
		}

	}

}
