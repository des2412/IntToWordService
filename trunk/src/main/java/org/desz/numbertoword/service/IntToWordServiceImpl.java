/**
 * 
 */
package org.desz.numbertoword.service;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Logger;

import org.desz.domain.NumberFrequency;
import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.IntToWordExc;
import org.desz.numbertoword.exceptions.IntToWordServiceException;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.factory.IntToWordEnumFactory;
import org.desz.numbertoword.mapper.INumberToWordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * 
 * @author des
 * 
 */
@Service
public final class IntToWordServiceImpl implements
		INumberToWordService<BigInteger> {

	private MongoTemplate mongoTemplate;

	protected final static Logger LOGGER = Logger
			.getLogger(IntToWordServiceImpl.class.getName());

	private String errMsg;

	@Autowired
	public IntToWordServiceImpl(MongoTemplate mongoTemplate) {
		super();
		this.mongoTemplate = mongoTemplate;
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
		try {
			saveFrequency(num);
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		}

		try {

			return intToWordMapper.getWord(new BigInteger(num));
		} catch (IntToWordExc e) {
			LOGGER.severe(e.getMessage());
			this.errMsg = intToWordMapper.getErrorMessage();
			throw new IntToWordServiceException(e.getMessage());
		}

	}

	@Override
	public void saveFrequency(String num) throws IntToWordServiceException {
		try {
			
			NumberFrequency freq = mongoTemplate.findOne(new Query(Criteria
					.where("number").is(num)), NumberFrequency.class);
			if(freq == null){
				mongoTemplate.insert(new NumberFrequency(num, 1));
			}
			else{
				LOGGER.info("NUMBER::" + freq.getNumber());

				LOGGER.info("CNT::" + freq.getCount());
				freq.setCount(freq.getCount() + 1);
				mongoTemplate.save(freq);
			}
			
		} catch (Exception e) {
			// LOGGER.severe(e.getMessage());
			throw (new IntToWordServiceException(e.getMessage()));
		}
	}

	@Override
	public String getErrorMessage() {
		return this.errMsg;
	}

}
