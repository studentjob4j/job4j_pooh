package ru.job4j.pooh;

/**
 * @author Evgenii Shegai
 * @version 1.0
 * @since 17.06.2021
 */

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PoohServerTest {

    //simple test for start travic ci com
    @Test
    public void whenGetOne() {
        PoohServer server = new PoohServer();
        assertThat(server.forTest(), is(1));
    }
}