package com.zonaRefri.ecommerce.dto;

import com.zonaRefri.ecommerce.entity.Address;
import com.zonaRefri.ecommerce.entity.Customer;
import com.zonaRefri.ecommerce.entity.Order;
import com.zonaRefri.ecommerce.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
