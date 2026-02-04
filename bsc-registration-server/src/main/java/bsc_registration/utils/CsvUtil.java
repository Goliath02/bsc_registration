package bsc_registration.utils;

import bsc_registration.feature.registration.dto.ExtraPerson;
import bsc_registration.feature.registration.dto.FinancialData;
import bsc_registration.feature.registration.dto.FormData;
import bsc_registration.feature.registration.dto.MainData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import static bsc_registration.utils.FormUtil.formatDate;
import static bsc_registration.utils.FormUtil.getTypeByBirthday;

@Service
public class CsvUtil {


    public String createCsvFromFormData(final FormData formData) {

        final MainData mainData = formData.mainData();
        final FinancialData financial = formData.financial();

        final List<ExtraPerson> extraPeople = formData.mainData().morePersons();

        final var csvFormat = CSVFormat.DEFAULT.withHeader().builder().build();

        final var stringWriter = new StringWriter();

        try {
            CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

            printHeader(csvPrinter);

            csvPrinter.printRecord(
                    mainData.reason(),
                    mainData.name(),
                    mainData.surename(),
                    mainData.gender(),
                    formatDate(mainData.birthday()),
                    mainData.email(),
                    mainData.phone(),
                    mainData.street(),
                    mainData.plz(),
                    mainData.place(),
                    formatDate(mainData.entryDate()),
                    mainData.type(),
                    getTypeByBirthday(mainData.birthday()),
                    financial.iban(),
                    financial.nameOfBankOwner(),
                    financial.sureNameBankOwner()
            );

            for (var extra : extraPeople) {
                csvPrinter.printRecord(
                        mainData.reason(),
                        extra.extraName(),
                        extra.extraSureName(),
                        extra.extraGender(),
                        formatDate(extra.extraBirthday()),
                        mainData.email(),
                        mainData.phone(),
                        mainData.street(),
                        mainData.plz(),
                        mainData.place(),
                        formatDate(mainData.entryDate()),
                        getTypeByBirthday(mainData.birthday()),
                        mainData.reason(),
                        financial.iban(),
                        financial.nameOfBankOwner(),
                        financial.sureNameBankOwner()
                );
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stringWriter.toString().trim();
    }

    private void printHeader(CSVPrinter csvPrinter) throws IOException {

        csvPrinter.printRecord(
                "Abteilung",
                "Vorname",
                "Nachname",
                "Geschlecht",
                "Geburtstag",
                "E-Mail",
                "Telefon",
                "Straße",
                "PLZ",
                "Ort",
                "Eintrittsdatum",
                "Beitragssätze",
                "Abteilung",
                "IBAN",
                "Vorname Konto",
                "Nachname Konto"
        );
    }
}
