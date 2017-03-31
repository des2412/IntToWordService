package org.desz.inttoword.conv;

import static org.desz.inttoword.language.ProvLangFactoryParts.DefUnit.SPC;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.desz.inttoword.language.NumericalLangMapping;
import org.desz.inttoword.language.ProvLangFactoryParts.DeUnit;

@FunctionalInterface
public interface CentIntConverter {

	String mapToWord(String number, NumericalLangMapping langMapping);
	/**
	 * 
	 * @param prm
	 * @param numericalLangMap
	 * @return
	 */
	static String hundredToWord(String prm,
			NumericalLangMapping numericalLangMap) {
		prm = Objects.requireNonNull(prm);

		final int n = Integer.parseInt(prm);
		final String key = String.valueOf(n);
		final String word = numericalLangMap.intWords().get(key);
		if (!Objects.isNull(word)) {
			return word.toLowerCase();
		}
		int nmod = n % 100;
		final String hun = numericalLangMap.intWords()
				.get(String.valueOf(n / 100));
		// if true it's whole hundreds
		if (nmod == 0) {
			return hun.toLowerCase() + numericalLangMap.getHund();

		}

		StringBuilder sb = new StringBuilder();
		// build non whole hundreds..

		if (!Objects.isNull(
				numericalLangMap.intWords().get(String.valueOf(nmod)))) {
			if (numericalLangMap.getBilln().equals(DeUnit.BILLS.val())) {
				sb.append(hun.toLowerCase() + numericalLangMap.getHund()
						+ numericalLangMap.intWords().get(String.valueOf(nmod))
								.toLowerCase());
			} else {
				sb.append(hun.toLowerCase() + numericalLangMap.getHund()
						+ SPC.val() + numericalLangMap.getAnd()
						+ numericalLangMap.intWords().get(String.valueOf(nmod))
								.toLowerCase());
			}

			return sb.toString();
		}

		// nmod % 10 > 0)
		int k = nmod;// e.g., nmod = 23
		nmod %= 10;// ..nmod = 3
		k -= nmod; // .. k == 20
		if (inRange(n)) {// 0 to 99.

			sb.append(numericalLangMap.intWords().get(String.valueOf(k))
					.toLowerCase() + SPC.val()
					+ numericalLangMap.intWords().get(String.valueOf(nmod))
							.toLowerCase());
			return sb.toString();

		}

		if (numericalLangMap.getBilln().contains(DeUnit.BILLS.val()))
			sb.append(hun.toLowerCase() + numericalLangMap.getHund()
					+ numericalLangMap.intWords().get(String.valueOf(nmod))
							.toLowerCase()
					+ numericalLangMap.getAnd() + numericalLangMap.intWords()
							.get(String.valueOf(k)).toLowerCase());
		else
			sb.append(hun.toLowerCase() + numericalLangMap.getHund()
					+ SPC.val() + numericalLangMap.getAnd()
					+ numericalLangMap.intWords().get(String.valueOf(k))
							.toLowerCase()
					+ SPC.val() + numericalLangMap.intWords()
							.get(String.valueOf(nmod)).toLowerCase());

		return sb.toString();
	}

	static boolean inRange(int i) {
		return IntStream.range(1, 100).boxed().collect(Collectors.toSet())
				.contains(i);
	}

}
