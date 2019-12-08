package org.desz.inttoword.converters;

import java.util.Optional;

import org.desz.inttoword.language.NumberWordMapping;

/*
 * convert hundredth to word.
 */
@FunctionalInterface
public interface IHundConverter {

	/**
	 * 
	 * @param number         the number.
	 * @param intWordMapping the NumberWordMapping.
	 * @return the word for the hundredth as specified by langMapping.
	 */
	Optional<String> toWordForLang(String number, NumberWordMapping intWordMapping);

}
