package org.desz.inttoword.converters;

import java.util.List;

import org.desz.inttoword.language.NumberWordMapping;
import org.desz.inttoword.results.Word.WordBuilder;

@FunctionalInterface
public interface ILongToWordBuilder<T> {

	T buildWord(List<String> numbers, WordBuilder wordBuilder, NumberWordMapping intWordMapping);

}
