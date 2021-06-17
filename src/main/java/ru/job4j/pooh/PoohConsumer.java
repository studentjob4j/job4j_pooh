package ru.job4j.pooh;

/**
 * @author Evgenii Shegai
 * @version 1.0
 * @since 17.06.2021
 * Отправляет запрос серверу и получает ответ на запрос
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PoohConsumer {

    public void startClient(String get) {
        try (Socket client = new Socket("127.0.0.1", 9000)) {
            try (OutputStream out = client.getOutputStream();
                 InputStream input = client.getInputStream()) {
                out.write("HTTP/1.1 200 OK\r\n".getBytes());
                out.write(get.getBytes(StandardCharsets.UTF_8));
                out.flush();
                byte[] buff = new byte[1_000_000];
                var total = input.read(buff);
                var text = new String(Arrays.copyOfRange(buff, 0, total), StandardCharsets.UTF_8);
                System.out.println(text);
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new PoohConsumer().startClient("GET/topic/weather/1");
       // new PoohConsumer().startClient("GET/queue/weather");
    }
}
