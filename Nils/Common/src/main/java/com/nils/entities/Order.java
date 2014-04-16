package com.nils.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;

/**
 * an order example
 * Created by kobi on 4/15/14.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = User.class, name = "Order")})
public class Order extends BaseEntity{
    String accountId;
    String description;
    Date date;

    public Order(){}

    public Order(String accountId, String description, Date date) {
        this.accountId = accountId;
        this.description = description;
        this.date = date;
    }

    public Order(String id, String name, String accountId, String description, Date date) {
        super(id, name);
        this.accountId = accountId;
        this.description = description;
        this.date = date;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
