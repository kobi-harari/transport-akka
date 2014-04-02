package com.nils.entities;

import java.util.List;

/**
 * Created by uri.silberstein on 4/1/14.
 */
public class User extends BaseEntity {
    int age;
    String accountId;

    public User(String id, String name, int age, String accountId) {
        super(id, name);
        this.age = age;
        this.accountId = accountId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
