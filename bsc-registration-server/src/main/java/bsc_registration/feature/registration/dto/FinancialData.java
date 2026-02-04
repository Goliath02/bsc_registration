package bsc_registration.feature.registration.dto;

public record FinancialData(
		String iban,
		String nameOfBankOwner,
		String sureNameBankOwner
) {
}
