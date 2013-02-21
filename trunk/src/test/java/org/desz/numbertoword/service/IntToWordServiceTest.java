package org.desz.numbertoword.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.desz.numbertoword.enums.EnumHolder.PROV_LANG;
import org.desz.numbertoword.exceptions.IntToWordServiceException;
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
@ComponentScan(basePackages = { "org.desz.spring.config",
		"org.desz.numbertoword.service" })
public class IntToWordServiceTest {

	ApplicationContext ctx;

	@Before
	public void init() {
		ctx = new AnnotationConfigApplicationContext(
				IntToWordServiceConfig.class);

	}

	@Test
	public void testConfigOk() {

		assertNotNull(ctx);
		assertNotNull(ctx.getBean("intToWordService"));

	}

	@Test
	public void testGetWord() throws IntToWordServiceException {
		IntToWordServiceImpl s = (IntToWordServiceImpl) ctx
				.getBean("intToWordService");
		String str = s.getWordInlang(PROV_LANG.UK, "100");

		assertEquals("One hundred", str);
	}

}
