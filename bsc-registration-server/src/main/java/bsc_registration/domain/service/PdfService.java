package bsc_registration.domain.service;

import bsc_registration.webInterface.dto.FormData;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class PdfService {

    private static final Color PRIMARY_COLOR = new Color(0, 0, 0);
    private static final Color GRAY = new Color(175, 42, 42);

    public byte[] generateRegistrationPdf(FormData formData) throws IOException {
        try (
                PDDocument document = new PDDocument();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);
            float margin = 50;

            // Kopfzeile
            drawHeader(content, document);

            // Titel
            drawTitle(content, "Anmeldung zum Vereinsbeitritt", margin, 710);

            float y = 680;
            y = drawSectionTitle(content, "Persönliche Daten", margin, y);
            y = drawPersonalDataSection(content, formData, margin, y);

            y = drawSectionTitle(content, "Mitgliedschaft", margin, y - 20);
            y = drawMembershipTypeSection(content, formData, margin, y);

            y = drawSectionTitle(content, "Einverständniserklärung", margin, y - 20);
            y = drawConsentSection(content, formData, margin, y);

            y = drawSectionTitle(content, "Unterschriften", margin, y - 20);
            y = drawSignatureSection(content, margin, y);

            drawFooter(content, margin);

            content.close();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }

    // ---------- Layout Helper ----------

    private void drawHeader(PDPageContentStream content, PDDocument document) throws IOException {
        try {
            InputStream logoStream = new ClassPathResource("images/Group.png").getInputStream();
            PDImageXObject logo = PDImageXObject.createFromByteArray(document, logoStream.readAllBytes(), "logo");
            content.drawImage(logo, 50, 750, 80, 80);
        } catch (Exception e) {
            // Placeholder
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
            content.newLineAtOffset(50, 780);
            content.showText("[Logo]");
            content.endText();
        }

        content.setNonStrokingColor(PRIMARY_COLOR);
        content.beginText();
        content.setFont(PDType1Font.HELVETICA_BOLD, 18);
        content.newLineAtOffset(300, 780);
        content.showText("1. BSC e. V.");
        content.endText();

        content.setStrokingColor(GRAY);
        drawLine(content, 50, 745, 545, 745);
        content.setStrokingColor(Color.BLACK);
    }

    private void drawTitle(PDPageContentStream content, String title, float x, float y) throws IOException {
        content.setNonStrokingColor(PRIMARY_COLOR);
        content.beginText();
        content.setFont(PDType1Font.HELVETICA_BOLD, 16);
        content.newLineAtOffset(x, y);
        content.showText(title);
        content.endText();
        content.setNonStrokingColor(Color.BLACK);
    }

    private float drawSectionTitle(PDPageContentStream content, String title, float x, float y) throws IOException {
        y -= 25;
        content.setNonStrokingColor(PRIMARY_COLOR);
        content.beginText();
        content.setFont(PDType1Font.HELVETICA_BOLD, 14);
        content.newLineAtOffset(x, y);
        content.showText(title);
        content.endText();

        content.setStrokingColor(PRIMARY_COLOR);
        drawLine(content, x, y - 3, 545, y - 3);
        content.setStrokingColor(Color.BLACK);
        content.setNonStrokingColor(Color.BLACK);

        return y - 15;
    }

    private float drawPersonalDataSection(PDPageContentStream content, FormData formData, float x, float y) throws IOException {
        y -= 10;

        // Main person data
        y = labeledField(content, "Vorname:", formData.mainData().name(), x, y);
        y = labeledField(content, "Nachname:", formData.mainData().surename(), x, y);
        y = labeledField(content, "Geburtsdatum:", String.valueOf(formData.mainData().birthday()), x, y);
        y = labeledField(content, "Geschlecht:", formData.mainData().gender(), x, y);
        y = labeledField(content, "E-Mail:", formData.mainData().email(), x, y);
        y = labeledField(content, "Telefon:", formData.mainData().phone(), x, y);
        y = labeledField(content, "Adresse:", buildFullAddress(formData), x, y);

        // Additional persons (if any)
        if (formData.mainData().morePersons() != null && ! formData.mainData().morePersons().isEmpty()) {
            y -= 20; // Extra space before additional persons

            for (int i = 0; i < formData.mainData().morePersons().size(); i++) {
                var extraPerson = formData.mainData().morePersons().get(i);

                // Section divider for additional person
                y -= 10;
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 12);
                content.newLineAtOffset(x, y);
                content.showText("Weitere Person " + (i + 1) + ":");
                content.endText();
                y -= 10;

                y = labeledField(content, "Vorname:", extraPerson.extraName(), x, y);
                y = labeledField(content, "Nachname:", extraPerson.extraSureName(), x, y);
                y = labeledField(content, "Geburtsdatum:", String.valueOf(extraPerson.extraBirthday()), x, y);
                y = labeledField(content, "Geschlecht:", extraPerson.extraGender(), x, y);
            }
        }

        return y;
    }

    private float drawMembershipTypeSection(PDPageContentStream content, FormData formData, float x, float y) throws IOException {
        String type = formData.mainData().type() != null ? formData.mainData().type().toUpperCase() : "";
        y -= 10;
        y = checkbox(content, "Mitglied", x, y, "MITGLIED".equals(type));
        y = checkbox(content, "Schüler/Student über 18", x + 200, y, "SCHÜLER/STUDENT ÜBER 18".equals(type));
        y = checkbox(content, "Familie", x + 400, y, "FAMILIE".equals(type));
        return y - 10;
    }

    private float drawConsentSection(PDPageContentStream content, FormData formData, float x, float y) throws IOException {
        y -= 15;
        drawCheckbox(content, x, y, formData.dataProtection());
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 11);
        content.newLineAtOffset(x + 20, y);
        content.showText("Ich akzeptiere die Satzung und Datenschutzbestimmungen des Vereins.");
        content.endText();
        return y - 15;
    }

    private float drawSignatureSection(PDPageContentStream content, float x, float y) throws IOException {
        y -= 15;
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 12);
        content.newLineAtOffset(x, y);
        content.showText("Ort, Datum:");
        content.endText();
        drawLine(content, x + 70, y - 3, x + 185, y - 3);

        content.beginText();
        content.newLineAtOffset(x + 200, y);
        content.showText("Unterschrift Mitglied:");
        content.endText();
        drawLine(content, x + 320, y - 3, 545, y - 3);

        y -= 35;
        content.beginText();
        content.newLineAtOffset(x, y);
        content.showText("Gegenzeichnung Verein:");
        content.endText();
        drawLine(content, x + 145, y - 3, 545, y - 3);
        return y - 20;
    }

    private void drawFooter(PDPageContentStream content, float x) throws IOException {
        content.setStrokingColor(GRAY);
        drawLine(content, x, 80, 545, 80);
        content.setStrokingColor(Color.BLACK);

        content.beginText();
        content.setFont(PDType1Font.HELVETICA_OBLIQUE, 9);
        content.newLineAtOffset(x, 65);
        content.showText("1. Badischer Schwimmclub Pforzheim |Heimsheimerstr. 1, 75233 Tiefenbronn | vorstand@erster-bsc-pforzheim.de | " +
                "erster-bsc-pforzheim.de");
        content.endText();
    }

    private float labeledField(PDPageContentStream content, String label, String value, float x, float y) throws IOException {
        y -= 20;
    
    // Fixed label width for alignment
    float labelWidth = 120;
    
    // Draw label
    content.beginText();
    content.setFont(PDType1Font.HELVETICA, 12);
    content.newLineAtOffset(x, y);
    content.showText(label);
    content.endText();
    
    // Draw value aligned at fixed position
    content.beginText();
    content.setFont(PDType1Font.HELVETICA, 12);
    content.newLineAtOffset(x + labelWidth, y);
    content.showText(value != null ? value : "");
    content.endText();
    
    // Draw line under the value
    drawLine(content, x, y - 3, 500, y - 3);
    return y;
}

    private float checkbox(PDPageContentStream content, String label, float x, float y, boolean checked) throws IOException {
        drawCheckbox(content, x, y, checked);
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 12);
        content.newLineAtOffset(x + 20, y);
        content.showText(label);
        content.endText();
        return y;
    }

    private void drawCheckbox(PDPageContentStream content, float x, float y, boolean checked) throws IOException {
        content.addRect(x, y - 2, 12, 12);
        content.stroke();
        if (checked) {
            content.moveTo(x + 2, y + 2);
            content.lineTo(x + 10, y + 10);
            content.moveTo(x + 10, y + 2);
            content.lineTo(x + 2, y + 10);
            content.stroke();
        }
    }

    private void drawLine(PDPageContentStream content, float x1, float y1, float x2, float y2) throws IOException {
        content.moveTo(x1, y1);
        content.lineTo(x2, y2);
        content.stroke();
    }


    private String buildFullAddress(FormData formData) {
        StringBuilder address = new StringBuilder();

        if (formData.mainData().street() != null && ! formData.mainData().street().isEmpty()) {
            address.append(formData.mainData().street());
        }

        if (formData.mainData().plz() != null && ! formData.mainData().plz().isEmpty()) {
            if (address.length() > 0) address.append(", ");
            address.append(formData.mainData().plz());
        }

        if (formData.mainData().place() != null && ! formData.mainData().place().isEmpty()) {
            if (address.length() > 0 && formData.mainData().plz() != null) {
                address.append(" ");
            } else if (address.length() > 0) {
                address.append(", ");
            }
            address.append(formData.mainData().place());
        }

        return address.toString();
    }

}