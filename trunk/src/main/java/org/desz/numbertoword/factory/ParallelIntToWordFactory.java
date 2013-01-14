package org.desz.numbertoword.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.mapper.IFNumberToWordMapper;
import org.desz.numbertoword.mapper.IntegerToWordMapper;
import org.desz.numbertoword.mapper.ParallelWorkerMapper;

public enum ParallelIntToWordFactory implements
		INumberToWordFactory<BigInteger> {

	UK_FAC();

	private final static Logger LOGGER = Logger
			.getLogger(ParallelIntToWordFactory.class.getName());

	@Override
	public IFNumberToWordMapper<BigInteger> getIntegerToWordMapper()
			throws NumberToWordFactoryException {

		IFNumberToWordMapper<BigInteger> integerToWordMapper = null;

		switch (this) {
		case UK_FAC:

			IntegerToWordMapper mapper = (IntegerToWordMapper) IntegerToWordEnumFactory.UK_FAC
					.getIntegerToWordMapper();
			integerToWordMapper = newMtIntegerToWordMapper(mapper);

			break;

		default:
			break;

		}
		return integerToWordMapper;
	}

	/**
	 * 
	 * @param mapper
	 * @return
	 * @throws NumberToWordFactoryException
	 */
	private IFNumberToWordMapper<BigInteger> newMtIntegerToWordMapper(
			IFNumberToWordMapper<BigInteger> mapper)
			throws NumberToWordFactoryException {
		// access private Constructor using reflection
		Constructor<?>[] constructors = ParallelWorkerMapper.class
				.getDeclaredConstructors();

		if (constructors.length == 1)
			constructors[0].setAccessible(true);
		else
			throw new NumberToWordFactoryException(
					"unexpected number of constructors");

		try {
			mapper = (IFNumberToWordMapper<BigInteger>) constructors[0]
					.newInstance(new Object[] { mapper });
		} catch (InstantiationException e1) {
			LOGGER.severe(e1.getMessage());
		} catch (IllegalAccessException e1) {
			LOGGER.severe(e1.getMessage());
		} catch (IllegalArgumentException e1) {
			LOGGER.severe(e1.getMessage());
		} catch (InvocationTargetException e1) {
			LOGGER.severe(e1.getMessage());
		}
		if (mapper == null) {
			throw new NumberToWordFactoryException(
					"Did not initialise MultiThreadNumberToWordMapper");
		}

		return mapper;
	}

}
