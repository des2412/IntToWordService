package org.desz.inttoword.results;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import static org.desz.inttoword.language.ProvLangValues.UkError.INVALID_INPUT;

import org.desz.inttoword.converters.ConversionDelegate;
import org.desz.inttoword.converters.HundredthConverter;
import org.desz.inttoword.converters.NumberFormatValidator;
import org.desz.inttoword.exceptions.AppConversionException;
import org.desz.inttoword.language.ProvLang;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestNumberFormatValidatorWithMock {

	ConversionDelegate delegate;

	@Before
	public void init() {
		delegate = new ConversionDelegate(new HundredthConverter());
		delegate.setNumberFormatValidator(NumberFormatValidator.getInstance());
	}

	@Test
	public void test() throws AppConversionException {

		NumberFormatValidator mock = mock(NumberFormatValidator.class);
		delegate.setNumberFormatValidator(mock);
		when(mock.isValidAndInRange(any())).thenReturn(false);
		String str = delegate.convertIntToWord(122, ProvLang.UK);
		assertNotNull(str);
		assertEquals(INVALID_INPUT.getError(), str);

		verify(mock).isValidAndInRange(any());
	}

}
