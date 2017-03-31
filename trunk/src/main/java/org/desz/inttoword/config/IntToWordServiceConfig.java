package org.desz.inttoword.config;

import java.util.Objects;
import java.util.Optional;

import org.desz.inttoword.conv.ConversionDelegate;
import org.desz.inttoword.repository.IntFreqRepoJpaRepository;
import org.desz.inttoword.service.INumberToWordService;
import org.desz.inttoword.service.IntToWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration(value = "intToWordServiceConfig")
@Profile({"dev", "cloud"})
@Import(value = {IntFreqRepoConfig.class})
public class IntToWordServiceConfig {
	protected final Logger log = LoggerFactory
			.getLogger(IntToWordServiceConfig.class);

	@Autowired()
	private IntFreqRepoConfig intFreqRepoConfig;

	@Autowired(required = false)
	@Qualifier(value = "cloudrepo")
	private String cloudrepo;

	@Bean
	public ConversionDelegate converterDelegate() {
		return new ConversionDelegate();
	}

	@Bean
	public INumberToWordService intToWordService() {
		Optional<IntFreqRepoJpaRepository> opt = Optional.empty();
		if (Objects.nonNull(cloudrepo)) {
			log.info("creating empty repository for IntToWordService");
			return new IntToWordService(opt, converterDelegate());
		}

		try {
			log.info("creating functional repository for IntToWordService");
			opt = Optional.of(intFreqRepoConfig.intFreqRepo());
		} catch (Exception e) {
			log.error("investigate:" + e.getMessage());

		}
		return new IntToWordService(opt, converterDelegate());
	}
}