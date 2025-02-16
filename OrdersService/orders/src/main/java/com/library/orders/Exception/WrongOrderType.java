package com.library.orders.Exception;

public class WrongOrderType extends  Exception{
    public WrongOrderType(String s) {
        super(s);
        System.out.println(s);
    }
}
