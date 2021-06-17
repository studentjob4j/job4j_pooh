package ru.job4j.pooh;

/**
 * @author Evgenii Shegai
 * @version 1.0
 * @since 17.06.2021
 * отправляет сообщение серверу и читает ответ - добавлено сообщение в сервер
 * или нет , заканчивает общение
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PoohProducer {

    public void startClient(String post) {
        try (Socket client = new Socket("127.0.0.1", 9000)) {
                try (OutputStream out = client.getOutputStream();
                     InputStream input = client.getInputStream()) {
                    out.write("HTTP/1.1 200 OK\r\n".getBytes());
                    out.write(post.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                    byte[] buff = new byte[1_000_000];
                    var total = input.read(buff);
                    var text = new String(Arrays.copyOfRange(buff, 0, total),
                            StandardCharsets.UTF_8);
                    System.out.println(text);
                    client.close();
                    input.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       new PoohProducer().startClient("POST/topic/weather -d \"temperature=18\"");
        //new PoohProducer().startClient("POST/queue/weather -d \"temperature=8\"");
    }
}
