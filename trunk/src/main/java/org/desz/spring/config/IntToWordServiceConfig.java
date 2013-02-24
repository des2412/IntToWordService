package org.desz.spring.config;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numbertoword.service.INumberToWordService;
import org.desz.numbertoword.service.IntToWordServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "intToWordConfig")
@ComponentScan({ "org.desz.spring.config" })
public class IntToWordServiceConfig {
	protected final static Logger LOGGER = Logger
			.getLogger(IntToWordServiceConfig.class.getName());

	@Bean(name = "intToWordService")
	public INumberToWordService<BigInteger> intToWordService() {
		try {
			return new IntToWordServiceImpl();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		}
		return null;
	}

}
