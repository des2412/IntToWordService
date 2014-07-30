/**
 * 
 */
package org.desz.numtoword.service;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numtoword.cms.ContentContainer.PROV_LANG;
import org.desz.numtoword.exceptions.IntToWordServiceException;
import org.desz.numtoword.mapper.RecursiveIntToWord;
import org.desz.numtoword.repository.NumberFrequencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author des Service Stereotype; update MongoDb and invokes the integer
 *         conversion.
 * 
 */
@Service
public final class IntToWordServiceImpl implements
		INumberToWordService<BigInteger> {

	protected final Logger LOGGER = Logger.getLogger(IntToWordServiceImpl.class
			.getName());

	private final NumberFrequencyRepository numberFrequencyRepository;

	@Autowired
	public IntToWordServiceImpl(
			NumberFrequencyRepository numberFrequencyRepository) {
		this.numberFrequencyRepository = numberFrequencyRepository;
	}

	@Override
	public String getWordInlang(PROV_LANG provLang, String num)
			throws IntToWordServiceException {
		numberFrequencyRepository.saveNumberFrequency(num);
		RecursiveIntToWord converter = new RecursiveIntToWord(provLang);
		try {
			return converter.convert(new StringBuilder(), Integer.valueOf(num));
		} catch (Exception e) {
			throw new IntToWordServiceException(e.getLocalizedMessage());
		}

	}

}
