package com.library.orders.Exception;

public class OrderNotFoundException extends Exception{
    public OrderNotFoundException(String s) {
        super(s);
        System.out.println(s);
    }
}
