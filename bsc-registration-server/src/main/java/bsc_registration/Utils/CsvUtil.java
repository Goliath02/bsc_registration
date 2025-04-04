package bsc_registration.Utils;

import bsc_registration.dto.FormData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;

import static bsc_registration.Utils.FormUtil.formatDate;
import static bsc_registration.Utils.FormUtil.getTypeByBirthday;

@Service
public class CsvUtil {


    public String createCsvFromFormData(final FormData formData) {

        final var mainData = formData.mainData();
        final var financial = formData.financial();

        final var extraPeople = formData.morePersons();

        final var csvFormat = CSVFormat.DEFAULT.withHeader().builder().build();

        final var stringWriter = new StringWriter();

        try {
            CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

            this.printHeader(csvPrinter);

            csvPrinter.printRecord(
                    mainData.name(),
                    mainData.surename(),
                    mainData.gender(),
                    formatDate(mainData.birthday()),
                    mainData.phone(),
                    mainData.street(),
                    mainData.place(),
                    formatDate(mainData.entryDate()),
                    mainData.plz(),
                    getTypeByBirthday(mainData.birthday()),
                    mainData.department(),
                    financial.iban(),
                    financial.nameOfBankOwner(),
                    financial.sureNameBankOwner()
            );

            for (var extra : extraPeople) {
                csvPrinter.printRecord(
                        extra.name(),
                        extra.surename(),
                        extra.gender(),
                        formatDate(extra.birthday()),
                        mainData.phone(),
                        mainData.street(),
                        mainData.place(),
                        mainData.plz(),
                        getTypeByBirthday(mainData.birthday()),
                        mainData.department(),
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
                "Vorname",
                "Nachname",
                "Geschlecht",
                "Geburtstag",
                "E-Mail",
                "Telefon",
                "Straße",
                "PLZ",
                "Beitragssätze",
                "Abteilung",
                "IBAN",
                "Vorname Konto",
                "Nachname Konto"
        );
    }
}
