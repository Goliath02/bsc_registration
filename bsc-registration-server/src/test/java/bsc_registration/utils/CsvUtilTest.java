package bsc_registration.utils;

import bsc_registration.dto.ExtraPerson;
import bsc_registration.dto.FinancialData;
import bsc_registration.dto.FormData;
import bsc_registration.dto.MainData;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CsvUtilTest {

	final CsvUtil csvUtil = new CsvUtil();

	@Test
	public void generateValidCsvWithData() {

		final String expected = """
				Abteilung,Vorname,Nachname,Geschlecht,Geburtstag,E-Mail,Telefon,Straße,PLZ,Ort,Eintrittsdatum,Beitragssätze,Abteilung,IBAN,Vorname Konto,Nachname Konto
				testSection,Name,Surename,Male,01/01/2025,"testemail@mail,com",123 123,testStreet,123,Place,01/01/2025,testType,Erwachsener,DE75512108001245126199,bank Owner,surename bankOwner
				testSection,extra1,sureanme,Female,01/01/2025,"testemail@mail,com",123 123,testStreet,123,Place,01/01/2025,Erwachsener,testSection,DE75512108001245126199,bank Owner,surename bankOwner""".replace("\n", "\r\n");

		MainData mainData = new MainData(
				"testType",
				"testSection",
				"Name",
				"Surename",
				LocalDate.of(2025, 1, 1),
				"Male",
				"testemail@mail,com",
				"123 123",
				"testStreet",
				"123",
				"Place",
				LocalDate.of(2025, 1, 1)
		);

		FinancialData financialData = new FinancialData(
				"DE75512108001245126199",
				"bank Owner",
				"surename bankOwner"
		);

		final ExtraPerson extraPerson = new ExtraPerson("extra1", "sureanme", LocalDate.of(2025, 1, 1), "Female");

		final FormData formData = new FormData(mainData, financialData, true, true, true, List.of(extraPerson));

		final String actual = csvUtil.createCsvFromFormData(formData);

		assertEquals(expected, actual);
	}


}