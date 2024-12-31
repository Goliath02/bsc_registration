package bsc_registration.dto;

public record FinancialData(
        String iban,
        String nameOfBankOwner,
        String sureNameBankOwner
) {
}
