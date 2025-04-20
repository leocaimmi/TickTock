package com.TickTock.TickTock.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.Period;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;  // Thymeleaf

    /**
     * Constructor for EmailService.
     * @param mailSender the JavaMailSender instance
     */
    @Autowired
    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
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
    public void sendBirthdayEmail(String email, String username, LocalDate birthDate) throws MessagingException {
        // Calcular la edad
        int age = Period.between(birthDate, LocalDate.now()).getYears();

        // Crear el contexto con los datos dinámicos
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("age", age);

        // Generar el contenido HTML utilizando Thymeleaf
        String htmlContent = templateEngine.process("birthdayUser.html", context);
        System.out.println("HTML Content: " + htmlContent);
        // Enviar el correo
        sendMessage(email, "🎉 ¡Feliz cumpleaños, " + username + "!", htmlContent);
    }


}
