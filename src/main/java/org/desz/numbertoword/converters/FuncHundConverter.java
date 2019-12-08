package org.desz.numbertoword.converters;

import java.util.Optional;

import org.desz.numbertoword.language.NumberWordMapping;

/*
 * convert hundredth to word.
 */
@FunctionalInterface
public interface FuncHundConverter {

	/**
	 * 
	 * @param number         the number.
	 * @param intWordMapping the NumberWordMapping.
	 * @return the word for the hundredth as specified by langMapping.
	 */
	Optional<String> wordForNumber(String number, NumberWordMapping intWordMapping);

}
