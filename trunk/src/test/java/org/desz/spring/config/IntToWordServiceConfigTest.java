package org.desz.spring.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.IntToWordServiceException;
import org.desz.numbertoword.service.INumberToWordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegerToWordServiceConfig.class })
@ComponentScan(basePackages = { "org.desz.numbertoword.service" })
public class IntToWordServiceConfigTest {

	@Autowired
	INumberToWordService<BigInteger> service;

	@Test
	public void testServiceInit() {
		assertNotNull(service);

	}

	@Test
	public void testServiceOps() throws IntToWordServiceException {
		String s = service.getWordInlang(PROV_LANG.UK, "1");
		assertNotNull(s);
		assertEquals("One", s);
	}

}
