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
     *
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
        context.setVariable("phrase",getPhrase());
        // Generar el contenido HTML utilizando Thymeleaf
        String htmlContent = templateEngine.process("birthdayUser.html", context);
        System.out.println("HTML Content: " + htmlContent);
        // Enviar el correo
        sendMessage(email, "🎉 ¡Feliz cumpleaños, " + username + "!", htmlContent);
    }

    private String getPhrase() {
        String[] Phrase = {
                "¡Te deseamos un día increíble lleno de alegría, amor y muchas sonrisas!",
                "Que cada momento de este día especial esté lleno de felicidad.",
                "¡Feliz cumpleaños! Que la vida te siga regalando razones para sonreír.",
                "Hoy celebramos tu vida, tu alegría y todo lo que te hace único.",
                "¡Que tu día esté repleto de amor, risas y momentos inolvidables!",

                "Felicidades, ahora sos oficialmente un año más sabio… o al menos más experimentado ",
                "¡Cumplís años pero seguís siendo tan joven como tu contraseña lo diga!",
                "¡No contés los años, contá las bendiciones (y las porciones de torta)!",
                "¿Otro año más? Tranquilo, nadie lo va a notar si no lo decís ",
                "Te deseo un cumpleaños tan increíble como vos… pero sin la parte de los lunes.",

                "Que este nuevo año te traiga crecimiento, luz y caminos nuevos por descubrir.",
                "El mejor regalo es vivir un año más lleno de sentido. ¡Feliz cumpleaños!",
                "Hoy no solo cumplís años, cumplís sueños, logros y aprendizajes.",
                "Que la vida te sorprenda con bendiciones aún más grandes que tus deseos.",
                "Cada año es una nueva página en tu historia. ¡Hacela épica!",

                "¡Gracias por existir y por dejar tu huella en nuestras vidas! ¡Feliz cumple!",
                "Celebrar tu cumpleaños es celebrar la suerte de tenerte cerca.",
                "Que la vida te devuelva con creces todo el amor que das.",
                "Sos de esas personas que hacen que todo valga la pena. ¡Felicidades!",
                "Brindo por vos, por tu risa contagiosa y por todo lo que está por venir.",

                "Le deseamos un cumpleaños lleno de éxitos personales y profesionales.",
                "Que este nuevo año de vida le traiga muchas satisfacciones.",
                "Un placer compartir el día a día con una persona tan dedicada. ¡Feliz cumpleaños!",
                "Le deseamos un excelente día y un año aún mejor.",
                "¡Felicitaciones por un año más de sabiduría y profesionalismo!",

                "Hoy se alinean los planetas para que la pases genial (o al menos eso dice el horóscopo).",
                "No es magia, es tu cumpleaños… ¡pero igual vamos a celebrarlo como si lo fuera!",
                "El calendario se puso de fiesta, ¡hoy es tu día!",
                "Hoy no envejecés… evolucionás. Como un Pokémon. ",
                "Tu existencia hace que el mundo sea un lugar mejor. Literal."
        };
        int randomIndex = (int) (Math.random() * Phrase.length);
        return Phrase[randomIndex];
    }


}
