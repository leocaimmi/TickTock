package com.TickTock.TickTock.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class BirthdayPhraseProvider {

    /**
     * List of birthday phrases.
     */
    private final List<String> phrases = List.of(
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
            "Tu existencia hace que el mundo sea un lugar mejor. Literal.",

            "Que cada deseo que pidas hoy sea el primer paso hacia un nuevo logro.",
            "Hoy no sumás años, sumás aventuras y aprendizajes. ¡Felicidades!",
            "El mundo celebra porque vos existís. ¡Feliz cumpleaños!",
            "Que cada sonrisa de hoy sea un presagio de un año espectacular.",

            "No estás envejeciendo, estás subiendo de nivel. ¡Bien jugado!",
            "Hoy sos el protagonista de esta aventura llamada vida. ¡Que disfrutes cada momento!",
            "Un año más para soñar en grande y cumplir metas aún más grandes.",
            "Que la felicidad te abrace hoy y no te suelte en todo el año.",

            "Los cumpleaños son la excusa perfecta para recordar lo increíble que sos.",
            "Que cada risa de hoy multiplique tu alegría durante todo el año.",
            "Hoy, la vida te hace una ovación de pie. ¡Te lo merecés!",
            "El paso del tiempo solo mejora a las personas excepcionales. ¡Feliz cumpleaños!",

            "Hoy no sólo celebramos tu nacimiento, celebramos todo lo bueno que sembrás.",
            "¡Que el año que empieza esté lleno de proyectos que te llenen el alma!",
            "Sos prueba de que los años no arrugan el espíritu. ¡Felicitaciones!",
            "Tu mejor versión siempre está por venir. ¡A disfrutar este nuevo ciclo!",

            "Cada año suma historias, risas y sueños cumplidos. ¡A por muchos más!",
            "No contés los años... contá las historias épicas que vas creando.",
            "El tiempo pasa, pero tu esencia sigue iluminando todo a su paso.",
            "Hoy te toca recibir todo el cariño que sembraste durante el año."
    );

    /**
     * List of available birthday phrases.
     */
    private final CopyOnWriteArrayList<String> availablePhrases = new CopyOnWriteArrayList<>(phrases);
    /**
     * Return a random birthday phrase.
     */
    public String getRandomPhrase() {
        if (availablePhrases.isEmpty()) {
            availablePhrases.addAll(phrases); // recarga la lista para el siguiente uso
        }
        int index = ThreadLocalRandom.current().nextInt(availablePhrases.size());
        return availablePhrases.remove(index);
    }
}
