package org.desz.spring.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.IntToWordServiceException;
import org.desz.numbertoword.service.INumberToWordService;
import org.desz.spring.config.IntToWordServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ComponentScan(basePackages = { "org.desz.spring.config", "org.desz.numbertoword.service" })
public class IntToWordServiceConfigTest {

	INumberToWordService<BigInteger> service;

	ApplicationContext ctx;

	@Before
	public void init() {
		ctx = new AnnotationConfigApplicationContext(
				IntToWordServiceConfig.class);
		service = ctx.getBean(INumberToWordService.class);

	}

	@Test
	public void testServiceInit() {
		assertNotNull(service);

	}
	
	@Test
	public void testServiceOps() throws IntToWordServiceException{
		String s = service.getWordInlang(PROV_LANG.UK, "1");
		assertNotNull(s);
		assertEquals("One", s);
	}

}
