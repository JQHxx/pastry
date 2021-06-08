package com.mrl.pastry.pay.utils;

/**
 * Check utilities
 *
 * @author MrL
 * @version 1.0
 * @date 2021/5/19
 */
public class CheckUtils {

    /**
     * 购物车商品 低16位存数值 高位存check状态
     */
    private final static int MASK = 1 << 16;

    /**
     * Determine whether the item is checked
     *
     * @param value the quantity of item
     * @return true: checked
     */
    public static boolean check(int value) {
        return (value >> 16) > 0;
    }

    /**
     * Check or uncheck the item
     *
     * @param value the quantity of item
     * @return value with the checked or unchecked status
     */
    public static int setChecked(int value) {
        return value ^ MASK;
    }

    /**
     * Get the quantity of item without status
     *
     * @param value the quantity of item
     * @return the value without status
     */
    public static int getQuantity(int value) {
        return value & (MASK - 1);
    }
}
