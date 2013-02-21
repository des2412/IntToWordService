package org.desz.spring.config;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numbertoword.service.INumberToWordService;
import org.desz.numbertoword.service.IntToWordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(value="intToWordConfig")
@ComponentScan({ "org.desz.spring.config" })
public class IntToWordServiceConfig {
	protected final static Logger LOGGER = Logger
			.getLogger(IntToWordServiceConfig.class.getName());

	private @Autowired
	NumberFrequencyRepositoryConfig repositoryConfig;

	@Bean(name = "intToWordService")
	public INumberToWordService<BigInteger> intToWordService() {
		try {
			return new IntToWordServiceImpl(
					repositoryConfig.numberFrequencyRepository());
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		} // Inject dependency
		return null;
	}

}
