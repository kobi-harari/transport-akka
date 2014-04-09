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

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", accountId='" + accountId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (age != user.age) return false;
        if (!accountId.equals(user.accountId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = age;
        result = 31 * result + accountId.hashCode();
        return result;
    }
}
