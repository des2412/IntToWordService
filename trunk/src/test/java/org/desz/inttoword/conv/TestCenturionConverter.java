package org.desz.inttoword.conv;

import static org.desz.inttoword.factory.ProvLangFactory.getInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.desz.inttoword.exceptions.ConversionParameterException;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.junit.Test;

import net.jodah.typetools.TypeResolver;

public class TestCenturionConverter {
	protected CenturionConverter<ConversionParameterException> centurionConverter = new CenturionConverterImpl();

	IntWordMapping lan = getInstance().getMapForProvLang(ProvLang.UK);
	@Test
	public void testMapToWord() throws Exception {

		assertNotNull(centurionConverter.hundrethToWord("123", lan));

	}

	@Test(expected = ConversionParameterException.class)
	public void testMapToWordFail() throws Exception {
		centurionConverter.hundrethToWord("1231",
				getInstance().getMapForProvLang(ProvLang.UK));

	}

	@Test
	public void testTypeResolution() {
		CenturionConverter<ConversionParameterException> cnv = new CenturionConverterImpl();

		Class<?>[] typeArgs = TypeResolver
				.resolveRawArguments(CenturionConverter.class, cnv.getClass());
		assertEquals(ConversionParameterException.class, typeArgs[0]);

		CenturionConverter<?> cnv2 = new CenturionConverterImpl();
		typeArgs = TypeResolver.resolveRawArguments(CenturionConverter.class,
				cnv2.getClass());
		assertEquals(ConversionParameterException.class, typeArgs[0]);

	}
}
