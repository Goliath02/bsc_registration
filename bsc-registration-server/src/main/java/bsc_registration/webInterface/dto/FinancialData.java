package bsc_registration.webInterface.dto;

public record FinancialData(
		String iban,
		String nameOfBankOwner,
		String sureNameBankOwner
) {
}
