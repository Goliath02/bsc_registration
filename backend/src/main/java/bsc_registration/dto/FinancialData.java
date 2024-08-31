package bsc_registration.dto;

public record FinancialData(
		String bankName,
		String bankPlace,
		String iban,
		String bic,
		String nameOfBankOwner,
		String sureNameBankOwner
) {
}
