package com.TickTock.TickTock.shared.infrastructure.notifications;

import com.TickTock.TickTock.birthday.application.dtos.response.BirthdayResponse;
import com.TickTock.TickTock.birthday.domain.services.BirthdayPhraseProvider;
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
import java.util.List;


@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;  // Thymeleaf
    private final BirthdayPhraseProvider birthdayPhraseProvider;
    /**
     * Constructor for EmailService.
     *
     * @param mailSender the JavaMailSender instance
     */
    @Autowired
    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine, BirthdayPhraseProvider birthdayPhraseProvider) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.birthdayPhraseProvider = birthdayPhraseProvider;
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
    public void sendBirthdayEmailUser(String email, String username, LocalDate birthDate) throws MessagingException {
        // Calcular la edad
        int age = calulateAge(birthDate);

        // Crear el contexto con los datos dinámicos
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("age", age);
        context.setVariable("phrase",birthdayPhraseProvider.getRandomPhrase());
        // Generar el contenido HTML utilizando Thymeleaf
        String htmlContent = templateEngine.process("birthdayUser.html", context);
        System.out.println("HTML Content: " + htmlContent);
        // Enviar el correo
        sendMessage(email, "🎉 ¡Feliz cumpleaños, " + username + "!", htmlContent);
    }


    /**
     * Sends a birthday list email to the user.
     *
     * @param email     the email address of the user
     * @param username  the name of the user
     * @param birthDate the list of birthdays
     */
    public void sendBirthdayListEmail(String email, String username, List<BirthdayResponse> birthDate) throws MessagingException {

        birthDate.forEach(b -> b.setPhrase(birthdayPhraseProvider.getRandomPhrase())); //designated phrase to each birthday

        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("birthdays", birthDate);


        String htmlContent = templateEngine.process("birthdayList.html", context);

        sendMessage(email, "🎉 ¡Lista mensual de cumpleaños agendados!, " + username + "!", htmlContent);
    }


    private int calulateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }




}
