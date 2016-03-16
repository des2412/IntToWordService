package org.desz.inttoword.spring.config;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.desz.inttoword.mapper.Int2StrConverter;
import org.desz.inttoword.repository.IntFreqRepoJpaRepository;
import org.desz.inttoword.service.IConverterService;
import org.desz.inttoword.service.ConversionSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration(value = "intToWordServiceConfig")
@Profile({ "dev", "cloud" })
@Import(value = { IntFreqRepoConfig.class })
public class IntToWordServiceConfig {
	protected final Logger log = LoggerFactory.getLogger(IntToWordServiceConfig.class);

	@Autowired()
	private IntFreqRepoConfig intFreqRepoConfig;

	@Autowired(required = false)
	@Qualifier(value = "cloudrepo")
	private String cloudrepo;

	@Bean // (name = "converterService")
	public Int2StrConverter converterService() {
		return new Int2StrConverter();
	}

	@Bean
	public IConverterService<BigInteger> intToWordService() {
		Optional<IntFreqRepoJpaRepository> opt = Optional.empty();
		if (Objects.nonNull(cloudrepo)) {
			log.info("creating empty repository for ConversionSrv");
			return new ConversionSrv(opt, converterService());
		}

		try {
			log.info("creating functional repository for ConversionSrv");
			opt = Optional.of(intFreqRepoConfig.intFreqRepo());
		} catch (Exception e) {
			log.error("investigate:" + e.getMessage());

		}
		return new ConversionSrv(opt, converterService());
	}
}