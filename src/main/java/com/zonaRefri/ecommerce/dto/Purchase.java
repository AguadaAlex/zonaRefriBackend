package com.zonaRefri.ecommerce.dto;

import com.zonaRefri.ecommerce.entity.Address;
import com.zonaRefri.ecommerce.entity.Customer;
import com.zonaRefri.ecommerce.entity.Order;
import com.zonaRefri.ecommerce.entity.OrderItem;

import java.util.Set;

public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
