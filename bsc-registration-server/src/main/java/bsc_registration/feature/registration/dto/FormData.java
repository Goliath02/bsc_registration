package bsc_registration.feature.registration.dto;

public record FormData(
        MainData mainData,
        FinancialData financial,
        boolean dataProtection,
        boolean correctness,
        boolean hiddenSecurityCheck

) {
}
