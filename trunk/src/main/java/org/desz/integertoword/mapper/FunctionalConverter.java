package org.desz.integertoword.mapper;

@FunctionalInterface
interface FunctionalConverter<F, T> {
	T convert(F from);
}
