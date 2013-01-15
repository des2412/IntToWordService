/**
 * 
 */
package org.desz.numbertoword.mapper;

import java.math.BigInteger;
import java.util.concurrent.Callable;

import org.desz.numbertoword.enums.EnumHolder.DEF_FMT;

/**
 * @author desz
 * @see ParallelIntToWord
 * 
 *      Usage each instance is responsible for converting a number [1-999]
 * 
 */
public class IntToWordWorker implements Callable<String> {

	private BigInteger num;
	private String order;

	private IFNumberToWordMapper<BigInteger> mapper;

	/**
	 * 
	 * @param num
	 *            BigInteger to convert
	 * @param order
	 * @param mapper
	 */
	public IntToWordWorker(BigInteger num, String order,
			IFNumberToWordMapper<BigInteger> mapper) {
		super();
		this.num = num;
		this.order = order;
		this.mapper = mapper;
	}

	@Override
	public String call() throws Exception {
		if (num.intValue() > 0) {

			return mapper.getWord(num) + DEF_FMT.SPACE.val() + this.order;
		}
		return DEF_FMT.EMPTY.val();

	}

}
