package bsc_registration.domain.service;


import bsc_registration.infrastructure.config.MailSenderConfig;
import bsc_registration.infrastructure.repository.BscMemberRepository;
import bsc_registration.webInterface.dto.BscNameMail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.String.format;


@Service
@RequiredArgsConstructor()
@Slf4j
@EnableAsync
public class EmailService {

    public static final String BASIC_MAIL_TITEL = "1.BSC Pforzheim Info";
    public static final String INVITE_TEMPLATE = "InviteTemplate";
    private final MailSenderConfig mailSenderConfig;

    private final BscMemberRepository bscMemberRepository;

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public void sendTrainerInviteMail(final String email, final String signUpKey) throws MessagingException, MailSendException, IOException {

        final var mailSender = mailSenderConfig.getJavaMailSender();

        final var message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(sendFrom));
        message.setRecipients(MimeMessage.RecipientType.TO, email);

        final var messageHelper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
        messageHelper.setFrom(sendFrom);
        messageHelper.setTo(InternetAddress.parse(email));
        messageHelper.setSubject("1.BSC Trainer-Einladung");

        messageHelper.setText(buildInviteMailHtml("1.BSC Trainer-Einladung", "Einladung", signUpKey), true);

        mailSender.send(message);
    }

    @Value("${mail.from}")
    private String sendFrom;

    public void sendInviteToVM() {

        final String messageForVM = """
                Liebe BSC-Mitglieder,
                <br><br>
                es ist wieder so weit – die Vereinsmeisterschaften Teil 2 stehen vor der Tür! 🎉
                Wir möchten alle Mitglieder herzlich einladen, dabei zu sein – ganz egal ob jung oder alt, ob Wettkampferfahren oder Freizeit-Schwimmer. Hauptsache, ihr habt Spaß im Wasser! 💦
                <br><br>
                Im Anhang findet ihr die Ausschreibung mit allen Disziplinen und Strecken. Dort seht ihr auch, welche Lagen und Distanzen für welche Altersgruppen empfohlen sind – aber keine Sorge: Das sind keine festen Vorgaben.
                Wenn du lieber eine kürzere Strecke schwimmen möchtest oder dich in einer anderen Lage ausprobieren willst – mach es einfach! Jeder darf mitmachen, so wie es für ihn oder sie am besten passt. 🙌
                <br><br>
                Wir freuen uns auf viele motivierte Schwimmerinnen und Schwimmer, spannende Läufe und vor allem auf einen Tag voller Teamgeist, Spaß und gemeinsamer BSC-Energie! ❤️
                <br><br>
                Also: Sei dabei, wenn wir gemeinsam zeigen, was in uns steckt! 🏆
                <br><br>
                Mit sportlichen Grüßen
                """;

        this.sendMailToAllBscMembers("1.BSC Vereinsmeisterschafter Teil 2 2025", "MailMessageTemplate", messageForVM, null);
    }

    public void sendInviteToXmas() {

        final String messageForXmas = """
                <!-- Betreff: Einladung zur Weihnachtsfeier des 1. BSC Pforzheim 🎄 -->
                
                                 Liebe Mitglieder, liebe Eltern, liebe Kinder,<br>
                                 <br>
                                 wir laden euch herzlich zu unserer diesjährigen <b>Weihnachtsfeier des 1. BSC Pforzheim</b> ein!<br>
                                 Die Feier findet am <b>6. Dezember um 14:30 Uhr</b> im <b>Bürgerhaus Buckenberg-Haidach</b> (Marienburger Str. 18, 75181 Pforzheim) statt.<br>
                                 <br>
                                 Euch erwartet ein gemütliches Beisammensein mit einem <b>Basteltisch für die Kinder</b> sowie einem kleinen <b>Überraschungsunterhaltungsprogramm</b>.<br>
                                 Außerdem freuen wir uns darauf, im Rahmen der Feier die <b>Ehrungen der Vereinsmeisterschaften</b> vorzunehmen.<br>
                                 <br>
                                 Wie jedes Jahr wird es einen <b>Kuchenverkauf</b> geben – dafür freuen wir uns sehr über Kuchen- oder auch <b>Salatspenden</b> für das warme Essen und die Getränke, die ebenfalls angeboten werden.<br>
                                 <br>
                                 Damit wir besser planen können, bitten wir euch, euch kurz über folgendes Formular anzumelden:<br>
                                 <br>
                                 👉 <b><a href="https://forms.office.com/e/5vM4QALD4A">Zur Anmeldung für die Weihnachtsfeier</a></b><br>
                                 <br>
                                 Dort könnt ihr uns mitteilen,<br>
                                 – ob ihr kommt,<br>
                                 – wie viele Personen ihr mitbringt und<br>
                                 – ob ihr Kuchen oder Salat spendet.<br>
                                 <br>
                                 Wir freuen uns auf einen schönen Nachmittag mit euch! ✨<br>
                                 <br>
                                 Mit vorweihnachtlichen Grüßen<br>
                                 <b>Euer Trainerteam und die Vorstandschaft des 1. BSC</b><br>
                """;

        this.sendMailToAllBscMembers("Einladung zur Weihnachtsfeier des 1. BSC Pforzheim \uD83C\uDF84", "MailMessageTemplate", messageForXmas, "1BSC Einladung Weihnachtsfeier 2025.pdf");
    }

    public void sendMailToAllBscMembers(final String title, final String templateFileName, final String message, final String attachmentTitle) {

        List<CompletableFuture<String>> futures = bscMemberRepository.findAll().stream()
                .filter(member -> member.getEmail() != null)
                .map(member -> CompletableFuture.supplyAsync(
                        () -> {
                            try {
                                sendMail(member.getEmail(), title, templateFileName, message, attachmentTitle);
                                return null; // Erfolg → kein Fehler
                            } catch (Exception e) {
                                log.error("Fehler beim Senden an {}: {}", member.getEmail(), e.getMessage());
                                return member.getEmail(); // Fehleradresse zurückgeben
                            }
                        }, executor
                ))
                .toList();

        List<String> failedAddresses = futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .toList();

        log.info("Fehlerhafte Mails: {}", failedAddresses);
    }

    @Async
    protected CompletableFuture<Void> sendMail(
            String receiverMail,
            String title,
            String templateFileName,
            String message,
            String attachmentFileName
    ) {
        return CompletableFuture.runAsync(() -> {
            try {
                String template = loadHtmlTemplate(templateFileName);

                if (message != null) {
                    template = insertMessageIntoTemplate(template, title, message);
                }

                final var mailSender = mailSenderConfig.getJavaMailSender();
                final var mail = mailSender.createMimeMessage();

                mail.setFrom(new InternetAddress(sendFrom));
                mail.setRecipients(MimeMessage.RecipientType.TO, receiverMail);

                final var helper = new MimeMessageHelper(mail, true, CharEncoding.UTF_8);
                helper.setFrom(sendFrom);
                helper.setTo(InternetAddress.parse(receiverMail));
                helper.setSubject(title);
                helper.setText(template, true);

                if (attachmentFileName != null && ! attachmentFileName.isEmpty()) {
                    final InputStreamSource attachment = loadPdfFromResource(attachmentFileName);
                    log.debug("Attachment file exists: {}", attachmentFileName);
                    helper.addAttachment(attachmentFileName, attachment);
                }

                mailSender.send(mail);
                log.info("Mail erfolgreich gesendet an: {}", receiverMail);
            } catch (Exception e) {
                log.error("Fehler beim Senden an {}: {}", receiverMail, e.getMessage(), e);
                throw new RuntimeException(e);
            }
        });
    }

    @Async
    public CompletableFuture<Void> sendMail(final BscNameMailInfo info) {
        try {
            String template = loadHtmlTemplate(info.templateFileName());

            if (info.message() != null) {
                template = insertMessageIntoTemplate(template, info.title(), info.message());
            }

            final var mailSender = mailSenderConfig.getJavaMailSender();

            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true, StandardCharsets.UTF_8.name());

            helper.setFrom(sendFrom);
            helper.setTo(InternetAddress.parse(info.receiverMail()));
            helper.setSubject(info.title());
            helper.setText(template, true);

            if (info.attachmentFileName() != null && ! info.attachmentFileName().isEmpty()) {
                helper.addAttachment(info.attachmentFileName(), info.csvFile());
            }

            if (info.files() != null) {
                for (MultipartFile file : info.files()) {
                    helper.addAttachment(file.getOriginalFilename(), file);
                }
            }

            mailSender.send(mail);
            log.info("Mail erfolgreich gesendet an {}", info.receiverMail());

            return CompletableFuture.completedFuture(null);

        } catch (Exception e) {
            log.error("Fehler beim Senden an {}: {}", info.receiverMail(), e.getMessage(), e);
            CompletableFuture<Void> failed = new CompletableFuture<>();
            failed.completeExceptionally(e);
            return failed;
        }
    }


    protected record BscNameMailInfo(
            String receiverMail, String title, String templateFileName, String message,
            String attachmentFileName,
            ByteArrayDataSource csvFile,
            List<MultipartFile> files
    ) {

        public BscNameMailInfo(
                String receiverMail,
                String title,
                String templateFileName,
                String message,
                String attachmentFileName,
                ByteArrayDataSource csvFile,
                List<MultipartFile> files
        ) {
            this.receiverMail = receiverMail;
            this.title = title;
            this.templateFileName = templateFileName;
            this.message = message;
            this.attachmentFileName = attachmentFileName;
            this.csvFile = csvFile;
            this.files = files;
        }

        public BscNameMailInfo(
                String receiverMail,
                String title,
                String templateFileName,
                String message,
                String attachmentFileName,
                List<MultipartFile> files
        ) {
            this(
                    receiverMail,
                    title,
                    templateFileName,
                    message,
                    attachmentFileName,
                    null,
                    files
            );
        }
    }

    public String loadHtmlTemplate(String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/email/" + filename + ".html");
        byte[] data = resource.getInputStream().readAllBytes();
        return new String(data, StandardCharsets.UTF_8);
    }

    public void sendInfoEmailToUser(final String email) throws MessagingException,
            MailSendException, IOException {

        final String template = loadHtmlTemplate("MailTemplate.html");

        final var mailSender = mailSenderConfig.getJavaMailSender();

        final var message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(sendFrom));
        message.setRecipients(MimeMessage.RecipientType.TO, email);

        final var messageHelper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
        messageHelper.setFrom(sendFrom);
        messageHelper.setTo(InternetAddress.parse(email));
        messageHelper.setSubject("1.BSC Pforzheim Info");

        messageHelper.setText(template, true);

        mailSender.send(message);
    }

    public void sendInfoEmailToUsers(final List<String> emails) throws
            IOException {

        final ArrayList<BscNameMail> invalidMails = new ArrayList<>();

        final String template = loadHtmlTemplate("MailTemplate.html");

        List<BscNameMail> receiversMails;

        if (emails.isEmpty()) {
            receiversMails =
                    bscMemberRepository.findAll()
                            .stream()
                            .filter(bscMember -> bscMember.getEmail() != null)
                            .map(member -> new BscNameMail(
                                    member.getName(),
                                    member.getEmail()
                            ))
                            .toList();
        } else {
            receiversMails = emails.stream().map(e -> new BscNameMail("anonymous", e)).toList();
        }
        final var mailSender = mailSenderConfig.getJavaMailSender();

        receiversMails.forEach(member -> {

            try {
                final var message = mailSender.createMimeMessage();

                message.setFrom(new InternetAddress(sendFrom));
                message.setRecipients(MimeMessage.RecipientType.TO, member.getEmail());

                final var messageHelper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
                messageHelper.setFrom(sendFrom);
                messageHelper.setTo(InternetAddress.parse(member.getEmail()));
                messageHelper.setSubject(BASIC_MAIL_TITEL);

                messageHelper.setText(template, true);

                mailSender.send(message);
            } catch (MailSendException e) {
                invalidMails.add(new BscNameMail(member.getName(), member.getEmail()));
                log.error("Error sending email to user: {}", member, e);
            } catch (MessagingException e) {
                log.error("Could nor authenticate with mail server: {}", e.getMessage());
            } catch (MailAuthenticationException e) {
                log.error("Error occurred: {}", e.getMessage());
            }
        });

        log.info("Sending email to {} users was successful from {}.", (receiversMails.size() - invalidMails.size()), invalidMails.size());

        if (! invalidMails.isEmpty()) {
            log.warn("The following emails could not be sent: {}", invalidMails);
        }
    }

    private String insertMessageIntoTemplate(final String template, final String title, final String message) {

        return template.replace("${title}", title)
                .replace("${message}", message);
    }

    private String buildInviteMailHtml(final String title, final String message, final String signUpKey) throws IOException {

        final String template = loadTemplate(INVITE_TEMPLATE);

        return template.replace("${title}", title)
                .replace("${message}", message)
                .replace("${link}", signUpKey);
    }

    private String buildLinkToken(final String signUpKey) {
        return format("https://registration.erster-bsc-pforzheim.de/sign-up?token=%s", signUpKey);
    }

    private String loadTemplate(final String fileName) throws IOException {
        var resource = new ClassPathResource("templates/email/" + fileName + ".html");
        return Files.readString(resource.getFile().toPath());
    }

    private InputStreamSource loadPdfFromResource(final String filename) throws IOException {
        final ClassPathResource resource = new ClassPathResource("attachments/" + filename);

        if (! resource.exists()) {
            throw new IOException("File not found with name: " + filename);
        }

        return resource;
    }

}
