package com.zonaRefri.ecommerce.service;

import com.zonaRefri.ecommerce.dto.Purchase;
import com.zonaRefri.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
