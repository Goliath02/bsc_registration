package bsc_registration.dto;

import java.util.List;

public record FormData(
		MainData mainData,
		FinancialData financial,
		Boolean dataProtection,
		Boolean correctness,
		Boolean hiddenSecurityCheck,
		List<ExtraPerson> morePersons
) {
}
