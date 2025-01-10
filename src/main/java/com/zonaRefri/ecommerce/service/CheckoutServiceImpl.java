package com.zonaRefri.ecommerce.service;

import com.zonaRefri.ecommerce.dao.CustomerRepository;
import com.zonaRefri.ecommerce.dto.Purchase;
import com.zonaRefri.ecommerce.dto.PurchaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl implements  CheckoutService{
    private CustomerRepository customerRepository;
    public CheckoutServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Override
    public PurchaseResponse placeOrder(Purchase purchase){
        return null;
    }
}
