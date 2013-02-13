/**
 * 
 */
package org.desz.numbertoword.service;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.IntToWordExc;
import org.desz.numbertoword.exceptions.IntToWordServiceException;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.factory.IntToWordEnumFactory;
import org.desz.numbertoword.mapper.INumberToWordMapper;
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

	protected final static Logger LOGGER = Logger
			.getLogger(IntToWordServiceImpl.class.getName());

	private String errMsg;

	private NumberFrequencyRepository repo;

	@Autowired
	public IntToWordServiceImpl(NumberFrequencyRepository repo) {
		super();
		this.repo = repo;
	}

	@Override
	public String intToWordService(PROV_LANG provLang, String num)
			throws IntToWordServiceException {

		INumberToWordMapper<BigInteger> intToWordMapper = null;
		try {
			intToWordMapper = IntToWordEnumFactory.getMapper(provLang);
		} catch (NumberToWordFactoryException e) {
			LOGGER.severe(e.getMessage());
			throw new IntToWordServiceException(e.getMessage());
		}
		/*try {
			saveFrequency(num);
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		}*/

		try {

			return intToWordMapper.getWord(new BigInteger(num));
		} catch (IntToWordExc e) {
			LOGGER.severe(e.getMessage());
			this.errMsg = intToWordMapper.getErrorMessage();
			throw new IntToWordServiceException(e.getMessage());
		}

	}

	

	@Override
	public String getErrorMessage() {
		return this.errMsg;
	}

}
