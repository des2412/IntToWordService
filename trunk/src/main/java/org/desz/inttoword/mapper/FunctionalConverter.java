package org.desz.inttoword.mapper;

@FunctionalInterface
interface FunctionalConverter<F, T> {
	T convert(F from);
}
