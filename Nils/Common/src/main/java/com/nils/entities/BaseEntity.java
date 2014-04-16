package com.nils.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 * Created by uri.silberstein on 4/1/14.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = User.class, name = "User"),
        @JsonSubTypes.Type(value = Order.class, name = "Order"),
        @JsonSubTypes.Type(value = OrderItem.class, name = "OrderItem"),
        @JsonSubTypes.Type(value = Account.class, name = "Account")})
public class BaseEntity implements Serializable {
    private String id;
    private String name;

    public BaseEntity() {
    }

    public BaseEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
