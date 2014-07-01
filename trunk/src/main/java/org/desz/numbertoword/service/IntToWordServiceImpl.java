/**
 * 
 */
package org.desz.numbertoword.service;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.IntToWordServiceException;
import org.desz.numbertoword.mapper.RecursiveIntToWord;
import org.desz.numbertoword.repository.NumberFrequencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author des
 * 
 */
@Service
public final class IntToWordServiceImpl implements
		INumberToWordService<BigInteger> {

	protected final Logger LOGGER = Logger.getLogger(IntToWordServiceImpl.class
			.getName());

	private String errMsg;

	private final NumberFrequencyRepository numberFrequencyRepository;

	@Autowired
	public IntToWordServiceImpl(NumberFrequencyRepository numberFrequencyRepository) {
		this.numberFrequencyRepository =  numberFrequencyRepository;
	}
	
	@Override
	public String getWordInlang(PROV_LANG provLang, String num)
			throws IntToWordServiceException {
		numberFrequencyRepository.saveNumberFrequency(num);
		RecursiveIntToWord converter = new RecursiveIntToWord(provLang);
		return converter.convert(new StringBuilder(), Integer.valueOf(num));
		
	}

//	@Override
//	public String getWordInlang(PROV_LANG provLang, String num)
//			throws IntToWordServiceException {
//
//		INumberToWordMapper<BigInteger> intToWordMapper = null;
//
//		numberFrequencyRepository.saveNumberFrequency(num);
//		try {
//			intToWordMapper = IntToWordEnumFactory.getMapper(provLang);
//		} catch (NumberToWordFactoryException e) {
//			LOGGER.severe(e.getMessage());
//			throw new IntToWordServiceException(e.getMessage());
//		}
//
//		try {
//
//			return intToWordMapper.getWord(new BigInteger(num));
//		} catch (IntToWordException e) {
//			LOGGER.severe(e.getMessage());
//			this.errMsg = intToWordMapper.getErrorMessage();
//			throw new IntToWordServiceException(e.getMessage());
//		}
//
//	}

	@Override
	public String getErrorMessage() {
		return this.errMsg;
	}

}
