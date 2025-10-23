package bsc_registration.domain.service;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PdfService {


    public byte[] generateRegistrationPdf(String outputPath, String name, String geburtsdatum, String adresse) throws IOException {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            InputStream logoStream = new ClassPathResource("images/Group.png").getInputStream();
            PDImageXObject logo = PDImageXObject.createFromByteArray(document, logoStream.readAllBytes(), "logo");
            content.drawImage(logo, 430, 720, 100, 100);

            // Titel
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 18);
            content.newLineAtOffset(50, 750);
            content.showText("Anmeldeformular – 1. BSC");
            content.endText();

            // Abschnitt: Persönliche Daten
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.newLineAtOffset(50, 700);
            content.showText("Name: " + name);
            content.newLineAtOffset(0, -20);
            content.showText("Geburtsdatum: " + geburtsdatum);
            content.newLineAtOffset(0, -20);
            content.showText("Adresse: " + adresse);
            content.newLineAtOffset(0, -40);
            content.showText("Hiermit melde ich mich verbindlich für den Verein an.");
            content.endText();

            // Unterschrift & Datum
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
            content.newLineAtOffset(50, 580);
            content.showText("Ort, Datum: ________________________________   " +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            content.newLineAtOffset(0, -40);
            content.showText("Unterschrift: ________________________________");
            content.endText();

            content.close();
            document.save(outputStream);

            byte[] pdfBytes = outputStream.toByteArray();
            return pdfBytes;
        }

    }


}
