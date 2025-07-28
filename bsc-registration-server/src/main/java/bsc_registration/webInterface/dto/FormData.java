package bsc_registration.webInterface.dto;

import java.util.List;

public record FormData(
        MainData mainData,
        FinancialData financial,
        boolean dataProtection,
        boolean correctness,
        boolean hiddenSecurityCheck,
        List<ExtraPerson> morePersons
) {
}
