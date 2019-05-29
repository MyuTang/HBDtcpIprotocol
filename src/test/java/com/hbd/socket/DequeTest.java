package com.hbd.socket;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author Ren Xunxiao
 * @date 2018/10/22
 * @copyright Hanboard
 * Created by Ren Xunxiao on 2018/10/22
 */
public class DequeTest {
    @Test
    public void test() {
        ConcurrentLinkedDeque<Integer> integers = new ConcurrentLinkedDeque<>();

        integers.push(0);
        integers.push(1);
        integers.push(2);
        integers.push(3);

        Assert.assertNotNull(integers.peek());
        Assert.assertEquals(3, integers.peek().intValue());
        Assert.assertEquals(3, integers.peekFirst().intValue());
        Assert.assertEquals(0, integers.peekLast().intValue());

        Assert.assertEquals(3, integers.pop().intValue());
        integers.pop();
        integers.pop();
        integers.pop();
        Assert.assertEquals(0, integers.size());
    }
}
