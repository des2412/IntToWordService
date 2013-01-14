package org.desz.numbertoword.mapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.desz.numbertoword.enums.EnumHolder.DEF_FMT;
import org.desz.numbertoword.exceptions.IntegerToWordException;
import org.desz.numbertoword.exceptions.IntegerToWordNegativeException;

public final class ParallelWorkerMapper implements
		IFNumberToWordMapper<BigInteger> {

	protected final static Logger LOGGER = Logger
			.getLogger(ParallelWorkerMapper.class.getName());

	private static final int nThreads = 8;

	private ExecutorService exec;

	@SuppressWarnings("unused")
	private IFNumberToWordMapper<BigInteger> mapper;

	private ParallelWorkerMapper(final IFNumberToWordMapper<BigInteger> mapper) {

		this.mapper = mapper;
		exec = Executors.newFixedThreadPool(nThreads);

	}

	/**
	 * parallel processing implementation
	 */
	@Override
	public String getWord(BigInteger num) throws IntegerToWordException,
			IntegerToWordNegativeException {

		String formattedNumber = null;
		try {
			formattedNumber = ((IntegerToWordMapper) mapper)
					.formatBigInteger(num);
		} catch (IntegerToWordException e) {
			LOGGER.info(e.getMessage());
			throw new IntegerToWordException(e);
		} catch (IntegerToWordNegativeException e) {
			throw new IntegerToWordNegativeException(e.getMessage());
		}

		String[] components = formattedNumber.split(DEF_FMT.NUM_SEP.val());
		final int nComps = components.length;

		IntToWordWorker worker = null;

		List<Callable<String>> tasks = new ArrayList<Callable<String>>();

		switch (nComps) {
		case 3:
			BigInteger unit = BigInteger.valueOf(Long.valueOf(components[0]));
			worker = new IntToWordWorker(unit, ((IntegerToWordMapper) mapper)
					.getEnumLanguageSupport().getMillUnit(), this.mapper);

			tasks.add(worker);

			unit = BigInteger.valueOf(Long.valueOf(components[1]));

			worker = new IntToWordWorker(unit, ((IntegerToWordMapper) mapper)
					.getEnumLanguageSupport().getThouUnit(), this.mapper);

			tasks.add(worker);

			unit = BigInteger.valueOf(Long.valueOf(components[2]));

			worker = new IntToWordWorker(unit, DEF_FMT.EMPTY.val(), this.mapper);

			tasks.add(worker);

			break;
		case 2:

			unit = BigInteger.valueOf(Long.valueOf(components[0]));
			worker = new IntToWordWorker(unit, ((IntegerToWordMapper) mapper)
					.getEnumLanguageSupport().getThouUnit(), this.mapper);
			tasks.add(worker);

			unit = BigInteger.valueOf(Long.valueOf(components[1]));

			worker = new IntToWordWorker(unit, DEF_FMT.EMPTY.val(), this.mapper);
			tasks.add(worker);

			break;
		case 1:
			unit = BigInteger.valueOf(Long.valueOf(components[0]));
			return mapper.getWord(unit);
		default:
			break;

		}

		List<Future<String>> futures = null;

		try {
			futures = exec.invokeAll(tasks);
		} catch (InterruptedException e1) {
			exec.shutdownNow();
			LOGGER.severe(e1.getMessage());
		}

		StringBuilder res = new StringBuilder();

		for (Future<String> fut : futures) {

			try {

				if (!fut.get().equals(DEF_FMT.EMPTY.val())) {

					res.append(fut.get().toLowerCase() + DEF_FMT.SPACE.val());
				}

			} catch (InterruptedException e) {
				LOGGER.severe(e.getLocalizedMessage());
			} catch (ExecutionException e) {
				LOGGER.severe(e.getLocalizedMessage());
			}
		}

		res.replace(0, 1, String.valueOf(res.charAt(0)).toUpperCase());
		String str = res.toString().trim();

		return str;
	}

	@Override
	public String getErrorMessage() {
		return mapper.getErrorMessage();
	}

}
