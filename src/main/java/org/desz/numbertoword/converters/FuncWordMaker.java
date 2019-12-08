package org.desz.numbertoword.converters;

import java.util.List;

import org.desz.numbertoword.language.NumberWordMapping;
import org.desz.numbertoword.results.Word.WordBuilder;

@FunctionalInterface
public interface FuncWordMaker<T> {

	T buildWord(List<String> numbers, WordBuilder wordBuilder, NumberWordMapping intWordMapping);

}
