package ru.job4j.pooh;

/**
 * @author Evgenii Shegai
 * @version 1.0
 * @since 17.06.2021
 * Общае хранилище для topic поэтому использую потокобезопасную коллекцию ConcurrentHashMap
 * но т.к у каждого топика
 * есть своя уникальная очередь то исп.простой int . Если объект просто добавлен то поле status = 0
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private   ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue =
            new ConcurrentHashMap<>();
    private int id;

    @Override
    public Resp process(Req req) {
        Resp result = null;

        if (req.text() == null) {
            id++;
            result = (new Resp(get(req, queue), id));
            return result;
        } else {
            queue.putIfAbsent(req.mode(), new ConcurrentLinkedQueue<>());
            queue.get(req.mode()).add(req.text());
        }

        return new Resp(req.text(), 0);
    }

    private String get(Req req, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue) {
        return queue.get(req.mode()).poll();
    }
}
