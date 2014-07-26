package org.desz.spring.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import org.desz.numtoword.cms.ContentContainer.PROV_LANG;
import org.desz.numtoword.exceptions.IntToWordServiceException;
import org.desz.numtoword.service.INumberToWordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegerToWordServiceConfig.class })
public class IntToWordServiceConfigTest {

	@Autowired
	private INumberToWordService<BigInteger> intToWordService;


	@Test
	public void testServiceOps() throws IntToWordServiceException {
		String s = intToWordService.getWordInlang(PROV_LANG.UK, "1");
		assertNotNull(s);
		assertEquals("one", s);
	}

}
