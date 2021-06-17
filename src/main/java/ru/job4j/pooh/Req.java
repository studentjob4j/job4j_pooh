package ru.job4j.pooh;

/**
 * @author Evgenii Shegai
 * @version 1.0
 * @since 17.06.2021
 * Класс парсит входящую строку и возвращает новый объект
 */

import java.util.Optional;

public class Req {

    private final String method;
    private final String mode;
    private final String text;

    private Req(String method, String mode, String text) {
        this.method = method;
        this.mode = mode;
        this.text = text;
    }

    public static Req of(String content) {
        /* TODO parse a content */
       Optional<Req> result = Optional.empty();
        String[] array = content.split("\r\n");
        String[] temp = array[1].split("/");
        if (temp[1].equals("queue")) {
            if (temp[0].equals("POST")) {
                String tempMethod = temp[1];
                String[] temp2 = temp[2].split(" ");
                String tempMode = temp2[0];
                result = Optional.of(new Req(tempMethod, tempMode, temp2[2]));
            } else if (temp[0].equals("GET")) {
                String tempMethod = temp[1];
                String tempMode = temp[2];
                result = Optional.of(new Req(tempMethod, tempMode, null));
            }
        } else if (temp[1].equals("topic")) {
            if (temp[0].equals("POST")) {
                String tempMethod = temp[1];
                String[] temp2 = temp[2].split(" ");
                String tempMode = temp2[0];
                result = Optional.of(new Req(tempMethod, tempMode, temp2[2]));
            } else if (temp[0].equals("GET")) {
                String tempMethod = temp[1];
                String tempMode = temp[2];
                result = Optional.of(new Req(tempMethod, tempMode, null));
            }
        }
        return result.orElse(new Req("no object", "no object", "no object"));
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String text() {
        return text;
    }

}
