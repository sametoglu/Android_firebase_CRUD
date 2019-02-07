package com.example.samet.firebase_crud.model;

/**
 * Created by samet on 22.12.2017.
 */

public class Customer {
    private String customerId;
    private String customerName;
    private String customerAddress;

    public Customer(){    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {        this.customerAddress = customerAddress;    }

    public Customer(String customerId, String customerName, String customerAddress){
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
    }
}