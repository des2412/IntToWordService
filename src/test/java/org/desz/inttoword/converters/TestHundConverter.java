package org.desz.inttoword.converters;

import static org.desz.inttoword.factory.ProvLangFactory.getInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.util.Optional;

import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.junit.Test;

public class TestHundConverter {
	IHundConverter hundConverter = new HundredthConverter();

	IntWordMapping mapping = getInstance().getMapForProvLang(ProvLang.UK);
	IntWordMapping mappingDe = getInstance().getMapForProvLang(ProvLang.DE);

	@Test
	public void testHundredthToWord() throws Exception {

		Optional<String> opt = hundConverter.toWordForLang("123", mapping);
		assertFalse(opt.isEmpty());
		assertEquals("one hundred and twenty three", opt.get());

	}

	@Test
	public void testHundredthToWord2() throws Exception {

		Optional<String> opt = hundConverter.toWordForLang("31", mapping);
		assertEquals("thirty one", opt.get());

	}

	@Test
	public void testHundredthToWord3() throws Exception {

		Optional<String> opt = hundConverter.toWordForLang("31", mappingDe);
		assertFalse(opt.isEmpty());
		assertNotEquals("einunddreißig", opt.get());

	}

	@Test
	public void testHundredthToWord4() throws Exception {

		Optional<String> opt = hundConverter.toWordForLang("287", mappingDe);
		assertFalse(opt.isEmpty());
		assertNotEquals("zweihundertsiebenundachtzig", opt.get());

	}

}
