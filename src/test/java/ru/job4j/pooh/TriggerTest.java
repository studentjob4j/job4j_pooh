package ru.job4j.pooh;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TriggerTest {

    @Test
    public void whenCreateTrigger() {
        Trigger trigger = new Trigger();
        int res = trigger.sum(5, 5);
        assertThat(res, is(10));
    }
}