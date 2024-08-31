package bsc_registration;

import bsc_registration.dto.ExtraPerson;
import bsc_registration.dto.FormData;
import bsc_registration.dto.MainData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Service
public class CsvUtil {


	public String createCsvFromFormData(FormData formData) {

		final MainData mainData = formData.mainData();
		List<ExtraPerson> extraPeople = formData.morePersons();

		CSVFormat csvFormat = CSVFormat.EXCEL.builder().build();

		StringWriter stringWriter = new StringWriter();

		try {
			CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

			csvPrinter.printRecord(mainData.name(), mainData.surename(), mainData.gender(), mainData.birthday());

			for (var extra: extraPeople) {
				csvPrinter.printRecord(extra.name(), extra.surename(), extra.gender(), extra.birthday());
			}


		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return stringWriter.toString().trim();


	}
}
