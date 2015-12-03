package org.desz.integertoword.spring.config;

import java.math.BigInteger;
import java.util.Optional;
import java.util.logging.Logger;

import org.desz.integertoword.repository.IFIntFreqRepo;
import org.desz.integertoword.service.IFIntToWordService;
import org.desz.integertoword.service.IntToWordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { IntFrequencyRepoConfig.class })
public class IntToWordServiceConfig {
	protected final Logger LOGGER = Logger.getLogger(IntToWordServiceConfig.class.getName());

	@Autowired
	private IntFrequencyRepoConfig intFreqRepoConfig;

	@Bean
	public IFIntToWordService<BigInteger> intToWordService() {

		Optional<IFIntFreqRepo> opt = Optional.empty();

		if (intFreqRepoConfig.intFreqRepo() == null)
			return new IntToWordServiceImpl(opt);

		opt = Optional.of(intFreqRepoConfig.intFreqRepo());
		return new IntToWordServiceImpl(opt);
	}

}
