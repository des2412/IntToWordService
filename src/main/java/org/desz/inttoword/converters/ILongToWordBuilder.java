package org.desz.inttoword.converters;

import java.util.List;

import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.results.Word;
import org.desz.inttoword.results.Word.WordBuilder;

@FunctionalInterface
public interface ILongToWordBuilder {

	Word buildWord(List<String> numbers, WordBuilder wordBuilder, IntWordMapping intWordMapping);

}
