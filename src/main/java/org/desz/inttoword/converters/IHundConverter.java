package org.desz.inttoword.converters;

import java.util.Optional;

import org.desz.inttoword.language.IntWordMapping;

/*
 * convert hundredth to word.
 */
@FunctionalInterface
public interface IHundConverter {

	/**
	 * 
	 * @param number         the number.
	 * @param intWordMapping the IntWordMapping.
	 * @return the word for the hundredth as specified by langMapping.
	 */
	Optional<String> toWordForLang(String number, IntWordMapping intWordMapping);

}
