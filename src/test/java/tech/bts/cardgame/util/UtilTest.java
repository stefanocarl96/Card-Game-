package tech.bts.cardgame.util;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void joinOnce() {

        String result = Util.join(Arrays.asList("one"), ",");

        assertThat(result, is("one"));
    }
}