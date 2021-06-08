package com.example.sellall.AdapterData;

public class UserBuyData {
            String name,quantity,no;

    public UserBuyData() {
    }

    public UserBuyData(String name, String quantity, String no) {
        this.name = name;
        this.quantity = quantity;
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getNo() {
        return no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
