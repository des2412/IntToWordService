package org.desz.inttoword.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.desz.inttoword.language.ILangProvider;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.desz.inttoword.language.ProvLangValues.DePair;
import org.desz.inttoword.language.ProvLangValues.DeUnit;
import org.desz.inttoword.language.ProvLangValues.FrPair;
import org.desz.inttoword.language.ProvLangValues.FrUnit;
import org.desz.inttoword.language.ProvLangValues.NlPair;
import org.desz.inttoword.language.ProvLangValues.NlUnit;
import org.desz.inttoword.language.ProvLangValues.UkPair;
import org.desz.inttoword.language.ProvLangValues.UkUnit;

/**
 * Factory for numeric and error values associated to supported ProvLang.
 * 
 * @author des
 * 
 */
public final class ProvLangFactory implements ILangProvider {

	private final Map<ProvLang, IntWordMapping> provLangStorageCache = new HashMap<ProvLang, IntWordMapping>();

	private static Logger log = Logger.getLogger(ProvLangFactory.class);

	/**
	 * enforce singleton contract.
	 */
	private ProvLangFactory() {
	}

	/**
	 * static class not loaded until referenced.
	 * 
	 * @author des
	 *
	 */
	private static class ProvLangHolder {
		private static final ILangProvider provLangStore = new ProvLangFactory();
	}

	/**
	 * 
	 * @return singleton instance.
	 */
	public static ILangProvider getInstance() {
		return ProvLangHolder.provLangStore;
	}

	@Override
	public IntWordMapping getMapForProvLang(final ProvLang p_provLang) {

		final ProvLang provLang = Objects.requireNonNull(p_provLang);
		synchronized (provLangStorageCache) {
			IntWordMapping.Builder builder = new IntWordMapping.Builder();
			switch (provLang) {
				case UK :

					builder.withBilln(UkUnit.BILLS.val());
					builder.withMilln(UkUnit.MILLS.val());
					builder.withThoud(UkUnit.THOUS.val());
					builder.withHund(UkUnit.HUNS.val());
					builder.withAnd(UkUnit.AND.val());
					builder.withIntToWordMap(Stream.of(UkPair.values()).collect(
							Collectors.toMap(UkPair::getNum, UkPair::getWord)));

					provLangStorageCache.put(ProvLang.UK, builder.build());

					break;
				case FR :

					builder.withBilln(FrUnit.BILLS.val());
					builder.withMilln(FrUnit.MILLS.val());
					builder.withThoud(FrUnit.THOUS.val());
					builder.withHund(FrUnit.HUNS.val());
					builder.withAnd(FrUnit.AND.val());
					builder.withIntToWordMap(Stream.of(FrPair.values()).collect(
							Collectors.toMap(FrPair::getNum, FrPair::getWord)));
					provLangStorageCache.put(ProvLang.FR, builder.build());

					break;

				case DE :

					builder.withBilln(DeUnit.BILLS.val());
					builder.withMilln(DeUnit.MILLS.val());
					builder.withThoud(DeUnit.THOUS.val());
					builder.withHund(DeUnit.HUNS.val());
					builder.withAnd(DeUnit.AND.val());
					builder.withIntToWordMap(Stream.of(DePair.values()).collect(
							Collectors.toMap(DePair::getNum, DePair::getWord)));
					provLangStorageCache.put(ProvLang.DE, builder.build());
					break;

				case NL :

					builder.withBilln(NlUnit.BILLS.val());
					builder.withMilln(NlUnit.MILLS.val());
					builder.withThoud(NlUnit.THOUS.val());
					builder.withHund(NlUnit.HUNS.val());
					builder.withAnd(NlUnit.AND.val());
					builder.withIntToWordMap(Stream.of(NlPair.values()).collect(
							Collectors.toMap(NlPair::getNum, NlPair::getWord)));
					provLangStorageCache.put(ProvLang.NL, builder.build());

					break;

				default :
					log.info("Unsupported language");
					break;

			}
		}
		return provLangStorageCache.get(provLang);
	}

}
