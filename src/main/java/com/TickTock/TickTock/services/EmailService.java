package com.TickTock.TickTock.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Constructor for EmailService.
     * @param mailSender the JavaMailSender instance
     */
    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendMessage(String email, String subject, String text) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, true); // true = HTML
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Sends a birthday email to the user.
     *
     * @param email     the email address of the user
     * @param username  the name of the user
     * @param birthDate the birth date of the user
     */
    public void sendBirthdayEmail(String email, String username, LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();

        String htmlContent = buildBirthdayHtml(username, age);
        sendMessage(email, "🎉 ¡Feliz cumpleaños, " + username + "!", htmlContent);
    }

    /**
     * Builds the HTML content for the birthday email.
     *
     * @param username the name of the user
     * @param age      the age of the user
     * @return the HTML content as a string
     */
    private String buildBirthdayHtml(String username, int age) {
        return """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <title>Feliz Cumpleaños</title>
                    <style>
                        body { font-family: 'Segoe UI', sans-serif; background-color: #f9f9f9; color: #333; text-align: center; padding: 30px; }
                        .card { background-color: #ffffff; border-radius: 15px; padding: 40px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); display: inline-block; max-width: 600px; }
                        h1 { color: #e91e63; font-size: 32px; }
                        h2 { color: #3f51b5; margin-top: 20px; }
                        p { font-size: 18px; margin-top: 20px; }
                        .emoji { font-size: 40px; }
                    </style>
                </head>
                <body>
                    <div class="card">
                        <div class="emoji">🎉🎂🥳</div>
                        <h1>¡Hoy es tu cumpleaños!</h1>
                        <h2>Feliz cumpleaños, <span style="color: #ff5722;">%s</span>!</h2>
                        <p>Hoy estás cumpliendo <strong>%d</strong> años. Te deseamos un día increíble lleno de alegría, amor y muchas sonrisas. 🎈</p>
                    </div>
                </body>
                </html>
                """.formatted(username, age);
    }
}
