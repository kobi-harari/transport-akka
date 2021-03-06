package com.transport.utils;

import com.nils.entities.Account;
import com.nils.entities.Order;
import com.nils.entities.OrderItem;
import com.nils.entities.User;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class MmRandom {

    Random random;
    String ENGLISH_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    String SPECIAL_CHARACTERS = "~`!@#$%^&*()_+-=?><;:'";

    public MmRandom() {
        this(System.currentTimeMillis());
    }

    public MmRandom(long seed) {
        System.out.println("using seed: " + seed);
        random = new Random(seed);
    }

    public Long nextLong() {
        return nextLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public Long nextLong(long i, long j) {
        return new Double(random.nextDouble() * j + i).longValue();
    }

    public Boolean nextBoolean() {
        return random.nextBoolean();
    }

    public Integer nextInt(int i) {
        return random.nextInt(i);
    }

    public Integer nextInt(int i, int j) {
        if (i == j) {
            return i;
        }
        if (i > j) {
            throw new IllegalArgumentException();
        }

        return nextInt(j - i) + i;
    }

    public String nextSpecialCharactersString() {
        return nextSpecialCharactersString(nextInt(5, 9));
    }

    public String nextSpecialCharactersString(int min, int max) {
        return nextSpecialCharactersString(nextInt(min, max));
    }


    private char[] nextCharArray(int length) {
        char[] charArray = new char[length];
        for (int i = 0; i < length; i++) {
            charArray[i] = ENGLISH_CHARACTERS.charAt(random.nextInt(ENGLISH_CHARACTERS.length()));
        }
        return charArray;
    }

    public String nextSpecialCharactersString(int length) {
        int specialChars = nextInt(0, length);
        char[] charArray = nextCharArray(length);
        charArray[nextInt(0, length - 1)] = SPECIAL_CHARACTERS.charAt(nextInt(0, SPECIAL_CHARACTERS.length()));
        specialChars--;
        while (specialChars > 0) {
            charArray[nextInt(0, length - 1)] = SPECIAL_CHARACTERS.charAt(nextInt(0, SPECIAL_CHARACTERS.length()));
            specialChars--;
        }
        return new String(charArray);
    }

    public String nextString(int length) {
        char[] text = nextCharArray(length);
        return new String(text);
    }

    public String nextString(int min, int max) {
        return nextString(nextInt(min, max));
    }

    public String nextString() {
        return nextString(nextInt(5, 9));
    }

    private long nextId() {
        return nextLong(1000000, 1000000000);
    }

    String akkaUserIdPrefix = "akka::user::";
    String akkaAccountIdPrefix = "akka::account::";
    String akkaOrderIdPrefix = "akka::order::";
    String akkaOrderItemIdPrefix = "akka::orderItem::";

    public User nextUser(String name) {
        return new User(akkaUserIdPrefix + nextInt(1000, 2000), name, nextInt(18, 67), UUID.randomUUID().toString());
    }

    public User nextUser() {
        return nextUser(nextString(5, 7));
    }

    public Account nextAccount(String name) {
        return new Account(akkaAccountIdPrefix + nextInt(1000, 2000), name);
    }

    public Account nextAccount() {
        return nextAccount(nextString(5, 7));
    }

    public Order nextOrder() { return nextOrder(nextString(5,7));
    }

    private Order nextOrder(String name) {
        return new Order(akkaOrderIdPrefix + nextInt(3000,4000),name,"akka:account::1",nextString(4,7),new Date());
    }
    public OrderItem nextOrderItem() { return nextOrderItem(nextString(5,7));
    }

    private OrderItem nextOrderItem(String name) {
        return new OrderItem(akkaOrderItemIdPrefix + nextInt(2000,3000),name,"akka:order::1",nextString(4,7),nextInt(7,15));
    }

}
