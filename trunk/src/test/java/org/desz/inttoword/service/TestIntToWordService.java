package org.desz.inttoword.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.math.BigInteger;

import org.desz.inttoword.exceptions.IntToWordServiceException;
import org.desz.inttoword.language.LanguageRepository.ProvLang;
import org.desz.inttoword.repository.IntFreqRepoJpaRepositoryImpl;
import org.desz.inttoword.service.IConverterService;
import org.desz.inttoword.spring.config.IntToWordServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntToWordServiceConfig.class })
@ActiveProfiles({ "dev", "cloud" })
public class TestIntToWordService {

	@Autowired
	private IConverterService<BigInteger> intToWordService;

	@Test
	public void testGetWord() throws IntToWordServiceException {

		assertEquals("one hundred", intToWordService.getWordInLang(ProvLang.UK, "100"));
	}

	@Test(expected = IntToWordServiceException.class)
	public void testWithEmptyProvLang() throws IntToWordServiceException {
		intToWordService.getWordInLang(ProvLang.EMPTY, "100");
	}

	/**
	 * ascertain non-availability does not block..
	 * 
	 * @throws IntToWordServiceException
	 */
	@Test
	public void testWithMockedRepo() throws IntToWordServiceException {
		IntFreqRepoJpaRepositoryImpl mock = mock(IntFreqRepoJpaRepositoryImpl.class);
		when(mock.isAvailable()).thenReturn(false);
		intToWordService.getWordInLang(ProvLang.UK, "100");
	}

}
