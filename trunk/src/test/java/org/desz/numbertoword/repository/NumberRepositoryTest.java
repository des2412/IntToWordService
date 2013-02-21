package org.desz.numbertoword.repository;

import static org.junit.Assert.fail;

import org.desz.spring.config.NumberFrequencyRepositoryConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(classes = NumberFrequencyRepositoryConfig.class, loader = AnnotationConfigContextLoader.class)
public class NumberRepositoryTest {

	NumberFrequencyRepository repo;

	ApplicationContext ctx;

	@Test
	public void test(){
		fail("not implemented");
	}
	

}
