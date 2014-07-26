package org.desz.spring.config;

import java.math.BigInteger;
import java.util.logging.Logger;

import org.desz.numtoword.service.INumberToWordService;
import org.desz.numtoword.service.IntToWordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { NumberFrequencyRepositoryConfig.class })
public class IntegerToWordServiceConfig {
	protected final Logger LOGGER = Logger
			.getLogger(IntegerToWordServiceConfig.class.getName());

	@Autowired
	private NumberFrequencyRepositoryConfig numberFrequencyRepositoryConfig;

	@Bean
	public INumberToWordService<BigInteger> intToWordService() {

		return new IntToWordServiceImpl(numberFrequencyRepositoryConfig.numberFrequencyRepository());
	}

}
