package org.jetlinks.core.utils;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class SerializeUtilsTest {


    @Test
    public void testPrimitive() {
        assertEquals(Byte.MAX_VALUE, codec(Byte.MAX_VALUE));
        assertEquals(Byte.MIN_VALUE, codec(Byte.MIN_VALUE));

        assertEquals(Short.MAX_VALUE, codec(Short.MAX_VALUE));
        assertEquals(Short.MIN_VALUE, codec(Short.MIN_VALUE));

        assertEquals(Integer.MAX_VALUE, codec(Integer.MAX_VALUE));
        assertEquals(Integer.MIN_VALUE, codec(Integer.MIN_VALUE));

        assertEquals(Long.MAX_VALUE, codec(Long.MAX_VALUE));
        assertEquals(Long.MIN_VALUE, codec(Long.MIN_VALUE));

        assertEquals(Float.MAX_VALUE, codec(Float.MAX_VALUE));
        assertEquals(Float.MIN_VALUE, codec(Float.MIN_VALUE));

        assertEquals(Double.MAX_VALUE, codec(Double.MAX_VALUE));
        assertEquals(Double.MIN_VALUE, codec(Double.MIN_VALUE));

    }

    @Test
    public void testString() {

        assertEquals("test", codec("test"));
        assertEquals("中文", codec("中文"));

        assertEquals("\r\n", codec("\r\n"));


    }

    @Test
    public void testArray() {

        assertArrayEquals(new int[]{1, 2, 3}, (int[]) codec(new int[]{1, 2, 3}));

        assertArrayEquals(new Integer[]{1, 2, 3}, (Integer[]) codec(new Integer[]{1, 2, 3}));


    }


    @Test
    public void testList() {

        assertEquals(Arrays.asList(1, 2, 3), codec(Arrays.asList(1, 2, 3)));

        assertEquals(Arrays.asList(1, "2", 3), codec(Arrays.asList(1, "2", 3)));


    }

    @Test
    public void testBigDecimal() {
        BigDecimal bigDecimal = new BigDecimal("12341315613123123789.12341231212312313312390123847289634591827634581723456789");
        assertEquals(bigDecimal, codec(bigDecimal));

        assertEquals(BigDecimal.ONE, codec(BigDecimal.ONE));
        assertEquals(BigDecimal.ZERO, codec(BigDecimal.ZERO));
        assertEquals(BigDecimal.valueOf(1.23), codec(BigDecimal.valueOf(1.23)));

    }

    @Test
    public void testBigInteger() {
        BigInteger integer = new BigInteger("123413156131231237891231924872019368451289734561289375641247856128745678234568");
        assertEquals(integer, codec(integer));

        assertEquals(BigInteger.ONE, codec(BigInteger.ONE));
        assertEquals(BigInteger.ZERO, codec(BigInteger.ZERO));
        assertEquals(BigInteger.valueOf(123123), codec(BigInteger.valueOf(123123)));

    }


    @Test
    public void testMap() {

        assertEquals(Collections.singletonMap("test", Arrays.asList(1, 2, 3)), codec(Collections.singletonMap("test", Arrays.asList(1, 2, 3))));


        assertEquals(Collections.singletonMap("test", null), codec(Collections.singletonMap("test", null)));

    }

    @SneakyThrows
    public Object codec(Object obj) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (ObjectOutputStream objOut = new ObjectOutputStream(output)) {
            SerializeUtils.writeObject(obj, objOut);
        }
        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        try (ObjectInputStream obIn = new ObjectInputStream(input)) {
            return SerializeUtils.readObject(obIn);
        }
    }
}