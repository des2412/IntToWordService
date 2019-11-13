package org.desz.inttoword.converters;

import static org.desz.inttoword.factory.ProvLangFactory.getInstance;
import static org.junit.Assert.assertEquals;
import java.util.Optional;

import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.junit.Test;

public class TestHundConverter {
	IHundConverter hundConverter = new HundredthConverter();

	IntWordMapping mapping = getInstance().getMapForProvLang(ProvLang.UK);

	@Test
	public void testHundredthToWord() throws Exception {

		Optional<String> opt = hundConverter.toWordForLang("123", mapping);
//		assertFalse(opt.isEmpty());
		assertEquals("one hundred and twenty three", opt.get());

	}

}
