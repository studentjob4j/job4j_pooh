package ru.job4j.pooh;

/**
 * @author Evgenii Shegai
 * @version 1.0
 * @since 17.06.2021
 * Многопоточный сервер количестово потоков ограничено количеством доступных ядер процессора
 * получает запрос от producer отвечает ему и далее получает запрос от consumer и выдает ответ на
 * запрос
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoohServer {

    private final HashMap<String, Service> modes = new HashMap<>();

    public void start() {
        modes.put("queue", new QueueService());
        modes.put("topic", new TopicService());
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        try (ServerSocket server = new ServerSocket(9000)) {
            while (true) {
                Socket socket = server.accept();
                pool.execute(() -> {
                    try (OutputStream out = socket.getOutputStream();
                         InputStream input = socket.getInputStream()) {
                        byte[] buff = new byte[1_000_000];
                        var total = input.read(buff);
                        var text = new String(Arrays.copyOfRange(buff, 0, total),
                                StandardCharsets.UTF_8);
                        System.out.println(text);
                        var req = Req.of(text);
                        Resp resp = modes.get(req.method()).process(req);
                        if (resp.status() == 0) {
                           String temp = ("HTTP/1.1 " + resp.status() + " OK объект был добавлен");
                           out.write((temp
                                   + System.getProperty("line.separator")).
                                   getBytes(StandardCharsets.UTF_8));
                        } else {
                            String temp = (resp.text());
                            out.write((temp
                                    + System.getProperty("line.separator")).
                                    getBytes(StandardCharsets.UTF_8));
                        }

                        out.flush();
                        System.out.println("server");
                        System.out.println();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int forTest() {
        return 1;
    }

    public static void main(String[] args) {
        new PoohServer().start();
    }
}
