package ru.job4j.pooh;

/**
 * @author Evgenii Shegai
 * @version 1.0
 * @since 17.06.2021
 * Общае хранилище для queue поэтому использую потокобезопасную коллекцию ConcurrentHashMap и
 * ItomicInteger  и synchronized .Если объект просто добавлен то поле status = 0
 */


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class QueueService implements Service {

    private  ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue =
            new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger();

    @Override
    public Resp process(Req req) {
       Resp result = null;
        if (req.text() == null) {
            result = (new Resp(get(req), id.incrementAndGet()));
            return result;
        }
        queue.putIfAbsent(req.mode(), new ConcurrentLinkedQueue<>());
        queue.get(req.mode()).add(req.text());
        return new Resp(req.text(), 0);
    }

    private synchronized String get(Req req) {
        return queue.get(req.mode()).poll();
    }
}
