package org.desz.inttoword.converters;

import static org.desz.inttoword.factory.ProvLangFactory.getInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Optional;

import org.desz.inttoword.language.NumberWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.junit.Test;

public class TestHundConverter {
	static final IHundConverter hundredthConverter = (j, k) -> (new HundredthConverter().toWordForLang(j, k));

	NumberWordMapping mapping = getInstance().getMapForProvLang(ProvLang.UK);
	NumberWordMapping mappingDe = getInstance().getMapForProvLang(ProvLang.DE);

	@Test
	public void test_123() throws Exception {

		Optional<String> opt = hundredthConverter.toWordForLang("123", mapping);
		// assertFalse(opt.isEmpty());
		assertEquals("one hundred and twenty three", opt.get());

	}

	@Test
	public void test_120() throws Exception {

		Optional<String> opt = hundredthConverter.toWordForLang("120", mapping);
		// assertFalse(opt.isEmpty());
		assertEquals("one hundred and twenty", opt.get());

	}

	@Test
	public void test_31() throws Exception {

		Optional<String> opt = hundredthConverter.toWordForLang("31", mapping);
		assertEquals("thirty one", opt.get());

	}

	@Test
	public void test_200() throws Exception {

		Optional<String> opt = hundredthConverter.toWordForLang("200", mappingDe);
		// assertFalse(opt.isEmpty());
		assertNotEquals("zwei hundert", opt.get());

	}

	@Test
	public void test_4() throws Exception {

		Optional<String> opt = hundredthConverter.toWordForLang("287", mappingDe);
		// assertFalse(opt.isEmpty());
		assertNotEquals("zweihundertsiebenundachtzig", opt.get());

	}

}
