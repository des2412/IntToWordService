package org.desz.integertoword.spring.config;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.integertoword.service.INumberToWordService;
import org.desz.integertoword.service.IntToWordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { NumberFrequencyRepositoryConfig.class })
public class IntToWordServiceConfig {
	protected final Logger LOGGER = Logger
			.getLogger(IntToWordServiceConfig.class.getName());

	@Autowired
	private NumberFrequencyRepositoryConfig numberFrequencyRepositoryConfig;

	@Bean
	public INumberToWordService<BigInteger> intToWordService() {

		return new IntToWordServiceImpl(
				numberFrequencyRepositoryConfig.numberFrequencyRepository());
	}

}
