package bsc_registration.webInterface.dto;

public record FormData(
		MainData mainData,
		FinancialData financial,
		boolean dataProtection,
		boolean correctness,
		boolean hiddenSecurityCheck

) {
}
