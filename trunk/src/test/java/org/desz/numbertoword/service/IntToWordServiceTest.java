package org.desz.numbertoword.service;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.IntToWordServiceException;
import org.desz.spring.config.IntegerToWordServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegerToWordServiceConfig.class })
public class IntToWordServiceTest {

	@Autowired
	private INumberToWordService<BigInteger> intToWordService;

	@Test
	public void testGetWord() throws IntToWordServiceException {

		String str = intToWordService.getWordInlang(PROV_LANG.UK, "100");

		assertEquals("one hundred", str);
	}

}
