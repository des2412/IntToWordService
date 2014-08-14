package org.desz.integertoword.service;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.desz.integertoword.content.ContentContainer.PROV_LANG;
import org.desz.integertoword.exceptions.IntToWordServiceException;
import org.desz.integertoword.service.INumberToWordService;
import org.desz.integertoword.spring.config.IntToWordServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntToWordServiceConfig.class })
public class IntToWordServiceTest {

	@Autowired
	private INumberToWordService<BigInteger> intToWordService;

	@Test
	public void testZero() throws IntToWordServiceException {
		String str = intToWordService.getWordInLang(PROV_LANG.UK, "0");
		assertEquals("zero", str);
	}

	@Test
	public void testGetWord() throws IntToWordServiceException {

		String str = intToWordService.getWordInLang(PROV_LANG.UK, "100");

		assertEquals("one hundred", str);
	}

}
