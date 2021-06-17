package ru.job4j.pooh;

/**
 * @author Evgenii Shegai
 * @version 1.0
 * @since 17.06.2021
 * Класс который возвращает Req после парсинга
 */

public class Resp {

    private final String text;
    private final int status;

    public Resp(String text, int status) {
        this.text = text;
        this.status = status;
    }

    public String text() {
        return text;
    }

    public int status() {
        return status;
    }
}
