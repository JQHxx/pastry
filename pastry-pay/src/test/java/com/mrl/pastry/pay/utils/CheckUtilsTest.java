package com.mrl.pastry.pay.utils;

import org.junit.Test;

/**
 * CheckUtils test
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/19
 */
public class CheckUtilsTest {

    @Test
    public void test() {
        int value = 100;
        System.out.println(CheckUtils.check(value));

        value = CheckUtils.setChecked(value);
        System.out.println(value);
        System.out.println(CheckUtils.getQuantity(value));
        System.out.println(CheckUtils.check(value));

        value++;
        value = CheckUtils.setChecked(value);
        System.out.println(value);
        System.out.println(CheckUtils.getQuantity(value));
        System.out.println(CheckUtils.check(value));
    }
}
