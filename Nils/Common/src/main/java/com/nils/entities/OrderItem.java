package com.nils.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Order item
 * Created by kobi on 4/15/14.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = User.class, name = "OrderItem")})
public class OrderItem extends BaseEntity {
    String orderId;
    String description;
    int price;

    public OrderItem(){}

    public OrderItem(String orderId, String description, int price) {
        this.orderId = orderId;
        this.description = description;
        this.price = price;
    }

    public OrderItem(String id, String name, String orderId, String description, int price) {
        super(id, name);
        this.orderId = orderId;
        this.description = description;
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
