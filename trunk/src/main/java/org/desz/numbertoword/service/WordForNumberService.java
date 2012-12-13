/**
 * 
 */
package org.desz.numbertoword.service;

import java.math.BigInteger;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.mapper.IFNumberToWordMapper;

/**
 * @author des
 * 
 */
public interface WordForNumberService<T extends Number> {
	/**
	 * 
	 * @param provLn
	 * @return mapper for provLn
	 */
	IFNumberToWordMapper<BigInteger> getIntegerToWordMapper(PROV_LANG provLn);

}
