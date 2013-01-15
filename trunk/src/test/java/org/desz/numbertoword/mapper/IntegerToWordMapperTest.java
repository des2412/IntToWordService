/**
 * 
 */
package org.desz.numbertoword.mapper;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author des
 * 
 */
public class IntegerToWordMapperTest {

	final static String[] decimals = { "One", "Two", "Three", "Four", "Five",
			"Six", "Seven", "Eight", "Nine" };
	static final protected List<String> l = Arrays.asList(decimals);

	protected INumberToWordMapper<BigInteger> intToWordMapper;

	protected final static Logger LOGGER = Logger
			.getLogger(IntegerToWordMapperTest.class.getName());

}
