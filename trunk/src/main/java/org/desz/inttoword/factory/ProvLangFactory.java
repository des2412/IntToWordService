package org.desz.inttoword.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.desz.inttoword.conv.ConversionDelegate;
import org.desz.inttoword.language.ILangProvider;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.desz.inttoword.language.ProvLangFactoryParts.DeIntWordPair;
import org.desz.inttoword.language.ProvLangFactoryParts.DeUnit;
import org.desz.inttoword.language.ProvLangFactoryParts.FrIntWordPair;
import org.desz.inttoword.language.ProvLangFactoryParts.FrUnit;
import org.desz.inttoword.language.ProvLangFactoryParts.NlIntWordPair;
import org.desz.inttoword.language.ProvLangFactoryParts.NlUnit;
import org.desz.inttoword.language.ProvLangFactoryParts.UkIntWordPair;
import org.desz.inttoword.language.ProvLangFactoryParts.UkUnit;
/**
 * Factory for numberic values associated to ProvLang.
 * 
 * @see ConversionDelegate
 * 
 * @author des
 * 
 */
public final class ProvLangFactory implements ILangProvider {

	private Map<ProvLang, IntWordMapping> provLangIntToWordCache = new HashMap<ProvLang, IntWordMapping>();

	private static volatile ILangProvider langProvider;
	private static Logger log = Logger.getLogger(ProvLangFactory.class);

	/**
	 * enforce singleton contract.
	 */
	private ProvLangFactory() {
	}

	/**
	 * 
	 * @return singleton instance.
	 */
	public static ILangProvider getInstance() {
		if (Objects.isNull(langProvider)) {
			synchronized (ILangProvider.class) {
				if (Objects.isNull(langProvider)) {
					langProvider = new ProvLangFactory();
				}
			}

		}
		return langProvider;
	}

	@Override
	public IntWordMapping getIntWordMap(final ProvLang p_provLang) {

		final ProvLang provLang = Objects.requireNonNull(p_provLang);
		synchronized (provLangIntToWordCache) {
			IntWordMapping.Builder builder = new IntWordMapping.Builder();
			switch (provLang) {
				case UK :

					builder.withBilln(UkUnit.BILLS.val());
					builder.withMilln(UkUnit.MILLS.val());
					builder.withThoud(UkUnit.THOUS.val());
					builder.withHund(UkUnit.HUNS.val());
					builder.withAnd(UkUnit.AND.val());
					builder.withIntToWordMap(Stream.of(UkIntWordPair.values())
							.collect(Collectors.toMap(UkIntWordPair::getNum,
									UkIntWordPair::getWord)));

					provLangIntToWordCache.put(ProvLang.UK, builder.build());

					break;
				case FR :

					builder.withBilln(FrUnit.BILLS.val());
					builder.withMilln(FrUnit.MILLS.val());
					builder.withThoud(FrUnit.THOUS.val());
					builder.withHund(FrUnit.HUNS.val());
					builder.withAnd(FrUnit.AND.val());
					builder.withIntToWordMap(Stream.of(FrIntWordPair.values())
							.collect(Collectors.toMap(FrIntWordPair::getNum,
									FrIntWordPair::getWord)));
					provLangIntToWordCache.put(ProvLang.FR, builder.build());

					break;

				case DE :

					builder.withBilln(DeUnit.BILLS.val());
					builder.withMilln(DeUnit.MILLS.val());
					builder.withThoud(DeUnit.THOUS.val());
					builder.withHund(DeUnit.HUNS.val());
					builder.withAnd(DeUnit.AND.val());
					builder.withIntToWordMap(Stream.of(DeIntWordPair.values())
							.collect(Collectors.toMap(DeIntWordPair::getNum,
									DeIntWordPair::getWord)));
					provLangIntToWordCache.put(ProvLang.DE, builder.build());
					break;

				case NL :

					builder.withBilln(NlUnit.BILLS.val());
					builder.withMilln(NlUnit.MILLS.val());
					builder.withThoud(NlUnit.THOUS.val());
					builder.withHund(NlUnit.HUNS.val());
					builder.withAnd(NlUnit.AND.val());
					builder.withIntToWordMap(Stream.of(NlIntWordPair.values())
							.collect(Collectors.toMap(NlIntWordPair::getNum,
									NlIntWordPair::getWord)));
					provLangIntToWordCache.put(ProvLang.NL, builder.build());

					break;

				default :
					log.info("Unsupported language");
					break;

			}
		}
		return provLangIntToWordCache.get(provLang);
	}

}
