package org.desz.integertoword.spring.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class IntToWordServiceConfigTest {

	@Autowired
	private INumberToWordService<BigInteger> intToWordService;


	@Test
	public void testServiceOps() throws IntToWordServiceException {
		String s = intToWordService.getWordInLang(PROV_LANG.UK, "1");
		assertNotNull(s);
		assertEquals("one", s);
	}

}
