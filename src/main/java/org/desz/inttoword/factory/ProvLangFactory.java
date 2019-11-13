package org.desz.inttoword.factory;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.of;

import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.requireNonNull;

import org.desz.inttoword.language.ILangProvider;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.IntWordMapping.IntWordMappingBuilder;
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

	private static final Map<ProvLang, IntWordMapping> provLangCache = new HashMap<ProvLang, IntWordMapping>();

	/**
	 * singleton.
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
	public IntWordMapping getMapForProvLang(final ProvLang pl) {
		IntWordMapping b = provLangCache.containsKey(pl) ? provLangCache.get(pl) : get(pl);
		return b;
	}

	private IntWordMapping get(final ProvLang pl) {
		final ProvLang provLang = requireNonNull(pl);
		synchronized (provLangCache) {
			IntWordMappingBuilder builder = IntWordMapping.builder();
			switch (provLang) {
			case UK:
				builder.quintn(UkUnit.QUINTS.val());
				builder.quadrn(UkUnit.QUADS.val());
				builder.trilln(UkUnit.TRILLS.val());
				builder.billn(UkUnit.BILLS.val());
				builder.milln(UkUnit.MILLS.val());
				builder.thoud(UkUnit.THOUS.val());
				builder.hund(UkUnit.HUNS.val());
				builder.and(UkUnit.AND.val());
				builder.intToWordMap(of(UkPair.values()).collect(toMap(UkPair::getNum, UkPair::getWord)));

				provLangCache.put(ProvLang.UK, builder.build());

				break;
			case FR:
				builder.quintn(FrUnit.QUINTS.val());
				builder.quadrn(FrUnit.QUADS.val());
				builder.trilln(FrUnit.TRILLS.val());
				builder.billn(FrUnit.BILLS.val());
				builder.milln(FrUnit.MILLS.val());
				builder.thoud(FrUnit.THOUS.val());
				builder.hund(FrUnit.HUNS.val());
				builder.and(FrUnit.AND.val());
				builder.intToWordMap(of(FrPair.values()).collect(toMap(FrPair::getNum, FrPair::getWord)));
				provLangCache.put(ProvLang.FR, builder.build());

				break;

			case DE:
				builder.quintn(DeUnit.QUINTS.val());
				builder.quadrn(DeUnit.QUADS.val());
				builder.trilln(DeUnit.TRILLS.val());
				builder.billn(DeUnit.BILLS.val());
				builder.milln(DeUnit.MILLS.val());
				builder.thoud(DeUnit.THOUS.val());
				builder.hund(DeUnit.HUNS.val());
				builder.and(DeUnit.AND.val());
				builder.intToWordMap(of(DePair.values()).collect(toMap(DePair::getNum, DePair::getWord)));
				provLangCache.put(ProvLang.DE, builder.build());
				break;

			case NL:
				builder.quintn(NlUnit.QUINTS.val());
				builder.quadrn(NlUnit.QUADS.val());
				builder.trilln(NlUnit.TRILLS.val());
				builder.billn(NlUnit.BILLS.val());
				builder.milln(NlUnit.MILLS.val());
				builder.thoud(NlUnit.THOUS.val());
				builder.hund(NlUnit.HUNS.val());
				builder.and(NlUnit.AND.val());
				builder.intToWordMap(of(NlPair.values()).collect(toMap(NlPair::getNum, NlPair::getWord)));
				provLangCache.put(ProvLang.NL, builder.build());

				break;

			default:
				break;

			}
		}
		return provLangCache.get(provLang);
	}

}
