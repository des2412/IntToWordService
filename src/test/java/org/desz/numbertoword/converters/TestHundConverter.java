package org.desz.numbertoword.converters;

import static org.desz.numbertoword.factory.ProvLangFactory.getInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Optional;

import org.desz.numbertoword.converters.FuncHundConverter;
import org.desz.numbertoword.converters.HundredthConverter;
import org.desz.numbertoword.language.NumberWordMapping;
import org.desz.numbertoword.language.ProvLang;
import org.junit.Test;

public class TestHundConverter {
	static final FuncHundConverter hundredthConverter = (j, k) -> (new HundredthConverter().wordForHundredth(j, k));

	NumberWordMapping mapping = getInstance().getMapForProvLang(ProvLang.UK);
	NumberWordMapping mappingDe = getInstance().getMapForProvLang(ProvLang.DE);

	@Test
	public void test_123() throws Exception {

		Optional<String> opt = hundredthConverter.wordForNumber("123", mapping);
		// assertFalse(opt.isEmpty());
		assertEquals("one hundred and twenty three", opt.get());

	}

	@Test
	public void test_120() throws Exception {

		Optional<String> opt = hundredthConverter.wordForNumber("120", mapping);
		// assertFalse(opt.isEmpty());
		assertEquals("one hundred and twenty", opt.get());

	}

	@Test
	public void test_31() throws Exception {

		Optional<String> opt = hundredthConverter.wordForNumber("31", mapping);
		assertEquals("thirty one", opt.get());

	}

	@Test
	public void test_200() throws Exception {

		Optional<String> opt = hundredthConverter.wordForNumber("200", mappingDe);
		// assertFalse(opt.isEmpty());
		assertNotEquals("zwei hundert", opt.get());

	}

	@Test
	public void test_4() throws Exception {

		Optional<String> opt = hundredthConverter.wordForNumber("287", mappingDe);
		// assertFalse(opt.isEmpty());
		assertNotEquals("zweihundertsiebenundachtzig", opt.get());

	}

}
