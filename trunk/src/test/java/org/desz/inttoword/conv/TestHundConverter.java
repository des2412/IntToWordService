package org.desz.inttoword.conv;

import static org.desz.inttoword.factory.ProvLangFactory.getInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.desz.inttoword.exceptions.ConversionParameterException;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.junit.Test;

import net.jodah.typetools.TypeResolver;

public class TestHundConverter {
	protected IHundConverter hundConverter = new HundConverter();

	IntWordMapping mapping = getInstance().getMapForProvLang(ProvLang.UK);
	@Test
	public void testMapToWord() throws Exception {

		assertNotNull(hundConverter.hundrethToWord("123", mapping));

	}

	@Test
	public void testMapToWordFail() throws Exception {
		hundConverter.hundrethToWord("1231",
				getInstance().getMapForProvLang(ProvLang.UK));

	}

}
